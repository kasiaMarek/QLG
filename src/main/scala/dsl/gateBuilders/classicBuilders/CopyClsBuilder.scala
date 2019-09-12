package dsl.gateBuilders.classicBuilders

import dsl.gates.DSLAnyGate
import dsl.gates.classic.CopyCls

class CopyClsBuilder extends AnyGateClsBuilder(1, "CPY") {
  override def getGate: DSLAnyGate = {
    beforeGetGate
    new CopyCls(args.head, out)
  }

}
