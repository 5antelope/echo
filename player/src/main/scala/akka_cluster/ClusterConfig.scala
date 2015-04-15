import akka.actor.{Props, ActorSystem}
import com.typesafe.config.ConfigFactory
import module.SongModel

/**
 * Created by yangwu on 4/9/15.
 */

case class ClusterConfig(songModel: SongModel) {

  val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + "33333").
    withFallback(ConfigFactory.load())

  val system = ActorSystem("ClusterSystem", config)

  val listener = system.actorOf(Props(classOf[SimpleClusterListener]), name = "SimpleClusterListener")

  def setUp(): Unit = {
    /** empty */
  }

  def broadcast(s:String): Unit = {
    println("- CHECK BROADCAST -" + s)
    listener ! requestPlay(s)
  }
}
