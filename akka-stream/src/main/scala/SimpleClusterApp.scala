import akka.actor.{Props, ActorSystem}
import com.typesafe.config.ConfigFactory

/**
 * Created by yangwu on 4/9/15.
 */

object SimpleClusterApp {

  def main(args: Array[String]): Unit = {

    val config = ConfigFactory.parseString("akka.remote.netty.tcp.port=" + "2552").
      withFallback(ConfigFactory.load())
    // Create an Akka system
    val system = ActorSystem("ClusterSystem", config)
    // Create an actor that handles cluster domain events
    val listener = system.actorOf(Props[SimpleClusterListener], name = "SimpleClusterListener")

    printOptions

    var option = scala.io.StdIn.readInt()
    while(option!=0){
      if (option == 1){
        listener ! requestPlay()
      }
      printOptions
      option = scala.io.StdIn.readInt()
    }
  }


  def printOptions = {
    println("0.\tExit")
    println("1.\tJoin")
    println("2.\tLeave")
    println("3.\tprint hosts")
  }
}