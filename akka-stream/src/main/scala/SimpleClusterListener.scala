import akka.actor.{Actor, ActorLogging}
import akka.cluster.ClusterEvent._
import akka.cluster.{MemberStatus, Cluster}
import akka.contrib.pattern.{DistributedPubSubExtension, DistributedPubSubMediator}

/**
 * Created by yangwu on 4/9/15.
 */

class SimpleClusterListener extends Actor with ActorLogging {
  import DistributedPubSubMediator.{ Subscribe, SubscribeAck }
  import DistributedPubSubMediator.Publish

  val cluster = Cluster(context.system)
  val members = cluster.state.members.filter(_.status == MemberStatus.Up);

  val mediator = DistributedPubSubExtension(context.system).mediator

  // subscribe to the topic named "content"
  mediator ! Subscribe("content", self)


  // subscribe to cluster changes, re-subscribe when restart
  override def preStart(): Unit = {
    //#subscribe
    cluster.subscribe(self, initialStateMode = InitialStateAsEvents,
      classOf[MemberEvent], classOf[UnreachableMember])
  }
  override def postStop(): Unit = cluster.unsubscribe(self)

  def receive = {
    case MemberUp(member) =>
      println("test up")
      log.info("Member is Up: {}", member.address)
      println("show members")
      for (a <- members){
        println(a.address)
      }

    case UnreachableMember(member) =>
      log.info("Member detected as unreachable: {}", member)
    case MemberRemoved(member, previousStatus) =>
      log.info("Member is Removed: {} after {}",
        member.address, previousStatus)
    case requestPlay()=>
      println("play")
      val file = new TestFile()
      mediator ! Publish("content", file)
    case SubscribeAck(Subscribe("content", None, `self`)) ⇒
      println("subAck")
    // context become ready
    case f:TestFile=>
      println(f.msg)


    case s: String ⇒
      log.info("Got {}", s)
    case _: MemberEvent => // ignore
  }

}