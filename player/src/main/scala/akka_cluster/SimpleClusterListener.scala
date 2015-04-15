import java.io.{FileOutputStream, File}

import akka.actor.{Actor, ActorLogging}
import akka.cluster.ClusterEvent._
import akka.cluster.{Cluster, MemberStatus}
import akka.contrib.pattern.{DistributedPubSubExtension, DistributedPubSubMediator}
import module._
import sample.cluster.simple.agree

import java.io._
import java.io.FileInputStream
import java.net.InetAddress;
import java.util.Date;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

/**
 * Created by yangwu on 4/9/15.
 */

class SimpleClusterListener() extends Actor with ActorLogging {

  import DistributedPubSubMediator.{Publish, Subscribe, SubscribeAck}

  val cluster = Cluster(context.system)
  val members = cluster.state.members.filter(_.status == MemberStatus.Up);

  val mediator = DistributedPubSubExtension(context.system).mediator

  var countAgree = 0
  var hassend = false

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

    case x:Play =>
      println("- CHECK PLAY -" + x.musicName)
      sender ! agree(x.musicName)

    case requestPlay(src:String) =>
      println(" -   BROADCASTING  - RECEIVE: " + src)
//      val songModel = new SongModel() {
//        url = new File("./music.mp3").toURI().toString()
//      }
      var sig = new Play
      sig.musicName_=(src)
      mediator ! Publish ("content", sig)


    case SubscribeAck(Subscribe("content", None, `self`)) =>
      println("subAck")
    // context become ready

    case songModel:SongModel =>
      songModel.mediaPlayer().play()

    case agree(src) =>
      println("- CHECK AGREE -" + src)
      val members = cluster.state.members.filter(_.status == MemberStatus.Up)
      countAgree+=1
      println("agree"+ countAgree + "size"+ members.size)
      if (countAgree > (members.size/2) && !hassend){
        println("send")
        hassend=true
        mediator ! Publish("content",transferMusic())
        val time = getNTPTime()
        mediator ! Publish("content",startTime(time+10000, src))
        println("over")
      }

      // TODO:
    //   case reject() =>


    case transferMusic()=>
      println("receive music")

    case s:startTime=>
      var cur = getNTPTime()
      println("start count down"+s.t + "current" + cur)
      val list = new PlayList()
      list.select(s.name) // update name
      println("- CHECK -  SRC: "+s.name+" REAL: "+MusicName.name)
      val url:String = new File("./"+MusicName.name+".mp3").toURI().toString()
      println("URL: "+url)
      SongModel.url = url
      while (s.t > cur){
        cur += 1
      }
      println("start play"+cur)

      SongModel.mediaPlayer().play()
      hassend=false

    case s: String ⇒
      log.info("Got {}", s)


    case _: MemberEvent => // ignore
  }

  def printToFile(f: java.io.File)(op: java.io.PrintWriter => Unit) {
    val p = new java.io.PrintWriter(f)
    try { op(p) } finally { p.close() }
  }

  def getNTPTime():Long= {
    val client = new NTPUDPClient();
    // We want to timeout if a response takes longer than 5 seconds
    client.setDefaultTimeout(5000);

      try {
        val hostAddr = InetAddress.getByName("ntp02.oal.ul.pt")
        val info = client.getTime(hostAddr);
        val date = new Date(info.getReturnTime());
        println(date)
        return date.getTime;
      }
      catch {
        case e:Exception=>
          client.close();
          e.printStackTrace();
      }
    //   client.close();
    return -1;
  }

}