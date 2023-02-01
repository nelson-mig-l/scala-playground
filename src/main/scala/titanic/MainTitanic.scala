package com.hatebit
package titanic

import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.{MultilayerPerceptronClassifier, RandomForestClassifier}
import org.apache.spark.ml.evaluation.{BinaryClassificationEvaluator, MulticlassClassificationEvaluator}
import org.apache.spark.ml.feature.{IndexToString, StringIndexer, VectorAssembler}
import org.apache.spark.ml.tuning.{CrossValidator, ParamGridBuilder}
import org.apache.spark.sql.SparkSession

object MainTitanic {

  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[1]")
      .appName("Titanic")
      .getOrCreate();
    spark.sparkContext.setLogLevel("ERROR")

    val rawTrainData = spark.read
      .schema(Schemas.trainSchema)
      .option("header", "true")
      .csv("data/titanic/train.csv")
    rawTrainData.show()

    val inputData = PrepareData.prepareData(rawTrainData)
    inputData.show()

    val Array(trainData, testData) = inputData.randomSplit(Array(0.7, 0.3))

    // --------------------------------------------------------

    val featuresCatColNames = Seq("Pclass", "Sex", "Embarked")
    val stringIndexers = featuresCatColNames.map { colName =>
      new StringIndexer()
        .setInputCol(colName)
        .setOutputCol(colName + "Indexed")
        .fit(trainData)
    }

    val labelIndexer = new StringIndexer()
      .setInputCol("Survived")
      .setOutputCol("SurvivedIndexed")
      .fit(trainData)

    val featuresNumColNames = Seq("Age", "SibSp", "Parch", "Fare")
    val indexedFeaturesCatColNames = featuresCatColNames.map(_ + "Indexed")
    val allIndexedFeaturesColNames = featuresNumColNames ++ indexedFeaturesCatColNames

    val assembler = new VectorAssembler()
      .setInputCols(Array(allIndexedFeaturesColNames: _*))
      .setOutputCol("Features")

    // ------------------------------------------------------

    // Random forest classifier
    val randomForest = new RandomForestClassifier()
      .setLabelCol("SurvivedIndexed")
      .setFeaturesCol("Features")

    // Multi layer perceptron classifier
    val layers = Array(7, 8, 16, 2)
    val mlp = new MultilayerPerceptronClassifier()
      .setLayers(layers)
      .setBlockSize(128)
      .setTol(1e-8)
      .setMaxIter(1000)
      .setLabelCol("SurvivedIndexed")
      .setFeaturesCol("Features")


    val labelConverter = new IndexToString()
      .setInputCol("prediction")
      .setOutputCol("predictedLabel")
      .setLabels(labelIndexer.labels)

    val pipeline = new Pipeline().setStages(
      //(stringIndexers :+ labelIndexer :+ assembler :+ randomForest :+ labelConverter).toArray)
      (stringIndexers :+ labelIndexer :+ assembler :+ mlp :+ labelConverter).toArray)

    val paramGrid = new ParamGridBuilder()
      .addGrid(randomForest.maxBins, Array(25, 28, 31))
      .addGrid(randomForest.maxDepth, Array(4, 6, 8))
      .addGrid(randomForest.impurity, Array("entropy", "gini"))
      .build()

    val evaluator = new BinaryClassificationEvaluator()
      .setLabelCol("SurvivedIndexed")

    val cv = new CrossValidator()
      .setEstimator(pipeline)
      .setEvaluator(evaluator)
      .setEstimatorParamMaps(paramGrid)
      .setNumFolds(10)

    println("Training")
    val crossValidatorModel = cv.fit(trainData)
    println("Training Done")

    println("Validate")
    val predictions = crossValidatorModel.transform(testData)
    val accuracy = evaluator.evaluate(predictions)

    println("Accuracy : " + accuracy)
    println("Error DT : " + (1.0 - accuracy))
  }
}