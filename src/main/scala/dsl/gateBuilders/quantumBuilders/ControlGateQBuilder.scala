package dsl.gateBuilders.quantumBuilders

import dsl.exceptions.{TooManyArgumentsException, VariableNotDefinedException}
import dsl.gateBuilders.AnyGateBuilder

abstract class ControlGateQBuilder(numOfArgs: Option[Int], controlsNum: Option[Int], gateName: String) extends AnyGateBuilder(numOfArgs, gateName){
  var controls: List[String] = List()

  override def consume(arg: String, declaredArgs: Set[String]): Unit = {
    if(arg.head == ':') {
      if(AnyGateBuilder.checkArgument(arg.tail, declaredArgs)) {
        controls :+= arg.tail
      } else {
        throw new VariableNotDefinedException(arg.tail)
      }
    } else {
      super.consume(arg, declaredArgs)
    }
  }

  override def beforeGetGate: Unit = {
    super.beforeGetGate
    checkArgNum(controls.size, controlsNum)
  }

}
