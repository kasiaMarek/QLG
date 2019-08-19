package dsl.gateBuilders.classicBuilders

import dsl.gates.DSLAnyGate
import dsl.gates.classic.NotCls

class NotClsBuilder extends AnyGateClsBuilder(1, "NOT") {
  override def getGate: DSLAnyGate = {
    beforeGetGate
    new NotCls(args.head, out)
  }
}
