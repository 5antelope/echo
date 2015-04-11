/**
 * Created by yangwu on 4/10/15.
 */

package sample.cluster.simple


sealed trait RemoteMessage
case class reject() extends RemoteMessage {

}
case class agree() extends RemoteMessage{

}
