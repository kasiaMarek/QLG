package dsl.gateBuilders.classicBuilders

import dsl.gates.DSLAnyGate
import dsl.gates.classic.AndCls

class AndClsBuilder() extends AnyGateClsBuilder(2, "AND") {
  override def getGate: DSLAnyGate = {
    beforeGetGate
    new AndCls((args(0), args(1)), out)
  }
}
