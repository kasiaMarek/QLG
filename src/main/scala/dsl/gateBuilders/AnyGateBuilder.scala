package dsl.gateBuilders

import dsl.exceptions.{NotEnoughArgumentsException, TooManyArgumentsException}
import dsl.gates.DSLAnyGate

abstract class AnyGateBuilder(val numOfArgs: Option[Int], val gateName: String) {
  var args: List[String] = List()

  def consume(arg: String, declaredArgs: Set[String]): Unit = {
    if(numOfArgs.isDefined && args.size >= numOfArgs.get) {
      throw new TooManyArgumentsException(gateName, numOfArgs.get)
    }
    if(AnyGateBuilder.checkArgument(arg, declaredArgs)) {
      args :+= arg
    }
  }

  def beforeGetGate: Unit = {
    if(numOfArgs.isDefined && args.size < numOfArgs.get) {
      throw new NotEnoughArgumentsException(gateName, numOfArgs.get)
    }
  }

  def getGate: DSLAnyGate

}

object AnyGateBuilder {
  def checkArgument(arg: String, declaredArgs: Set[String]): Boolean = declaredArgs.contains(arg)
  def checkVariable(arg: String, declaredArgs: Set[String]): Boolean =
    arg.matches("[a-z_][a-zA-Z_]*") && !declaredArgs.contains(arg)
}
