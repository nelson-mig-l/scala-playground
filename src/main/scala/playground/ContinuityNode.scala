package com.hatebit
package playground

import org.geotools.graph.structure.basic.BasicNode


class ContinuityNode(obj: Int) extends BasicNode {
  super.setObject(obj)

  override def hashCode(): Int = obj.hashCode

  override def equals(that: Any): Boolean = that match {
    case that: ContinuityNode => that.getObject.equals(obj)
    case _ => false
  }

}