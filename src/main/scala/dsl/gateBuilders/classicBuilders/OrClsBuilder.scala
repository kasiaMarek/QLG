package dsl.gateBuilders.classicBuilders

import dsl.gates.DSLAnyGate
import dsl.gates.classic.OrCls

class OrClsBuilder extends AnyGateClsBuilder(2, "OR") {
  override def getGate: DSLAnyGate = {
    beforeGetGate
    new OrCls((args.head, args(1)), out)
  }
}
