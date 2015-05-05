# Echo - Distributed Music System

Overview
---------------------------

Echo is a distributed music share application developed in Scala.

With Echo you can share your music with your friends. Play at same time in different locations.

![ScreenShot](https://raw.github.com/wy4515/Echo/master/player/login.png)

enter seed node's: `ip & port` (local ip & post if want to create a cluster)

![ScreenShot](https://raw.github.com/wy4515/Echo/master/player/UI.png)

dynamically monitor member list. Echo can easily scala up / down within the cluster

![ScreenShot](https://raw.github.com/wy4515/Echo/master/player/Propose.png)

when a node propose a song, a notification will pop up in multicast manner collection voting from members (time out enabled)

a decision will be made based on accumulated voting result

Features
---------------------------
- propose a song and wait for majorities to agree
- proposal time out after 10 sec.
- synchronized media player

*Enjoy and have fun with music!
