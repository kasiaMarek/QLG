package dsl.gateBuilders.quantumBuilders

import dsl.gateBuilders.AnyGateBuilder
import dsl.gates.DSLAnyGate
import dsl.gates.dslquantum.HadamardQ

class HadamardQBuilder extends AnyGateBuilder(Some(1), "HDM"){
  override def getGate: DSLAnyGate = {
    beforeGetGate
    new HadamardQ(args.head)
  }
}
