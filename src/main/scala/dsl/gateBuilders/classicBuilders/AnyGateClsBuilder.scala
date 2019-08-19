package dsl.gateBuilders.classicBuilders

import dsl.exceptions.{ArgumentAlreadyDefinedException, ClsGateNeedsToBeAssignToAValue}
import dsl.gateBuilders.AnyGateBuilder

abstract class AnyGateClsBuilder(numOfArgs: Int, gateName: String)
  extends AnyGateBuilder(Some(numOfArgs), gateName) {
  var out: String = _

  def setOut(out: String, declaredArgs: Set[String]) {
    if(!AnyGateBuilder.checkVariable(out, declaredArgs)) {
      throw new ArgumentAlreadyDefinedException(out)
    } else {
      this.out = out
    }
  }

  override def beforeGetGate: Unit = {
    super.beforeGetGate
    if(out == null) {
      throw new ClsGateNeedsToBeAssignToAValue()
    }
  }

}

