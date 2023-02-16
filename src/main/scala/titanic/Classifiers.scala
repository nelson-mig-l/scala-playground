package com.hatebit
package titanic

import org.apache.spark.ml.classification.{Classifier, MultilayerPerceptronClassifier, RandomForestClassifier}

object Classifiers {

  def randomForest() : RandomForestClassifier = {
    new RandomForestClassifier()
      .setLabelCol("SurvivedIndexed")
      .setFeaturesCol("Features")
  }

  def multilayerPerceptron() : MultilayerPerceptronClassifier = {
    val layers = Array(7, 8, 16, 2)
    new MultilayerPerceptronClassifier()
      .setLayers(layers)
      .setBlockSize(128)
      .setTol(1e-8)
      .setMaxIter(1000)
      .setLabelCol("SurvivedIndexed")
      .setFeaturesCol("Features")
  }

}
