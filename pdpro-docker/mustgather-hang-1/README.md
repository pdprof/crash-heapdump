# MustGather Hands-On Hang Case

## Start pdpro container on docker or openshift

Follow steps described at parent folder's README 


## Prepare trapit to catch error message

Login to pod/container and start trap it to gather document at the problem time.

- docker exec -it pdpro bash

- /config/trapit -e TRAS0112W -f /logs/messages.log -i 2 -t /config/mustgather.sh

```
$ docker exec -it pdpro bash
bash-4.4$ /config/trapit -e TRAS0112W -f /logs/messages.log -i 2 -t /config/mustgather.sh
trapit: Match found in /logs/messages.log
Fri Jul 2 12:13:59 UTC 2021 MustGather>> linperf.sh script starting...
Fri Jul 2 12:13:59 UTC 2021 MustGather>> Script version:  2016.05.31.
Fri Jul 2 12:13:59 UTC 2021 MustGather>> PROBLEMATIC_PID is:  1
Fri Jul 2 12:13:59 UTC 2021 MustGather>> SCRIPT_SPAN = 240
Fri Jul 2 12:13:59 UTC 2021 MustGather>> JAVACORE_INTERVAL = 30
Fri Jul 2 12:13:59 UTC 2021 MustGather>> TOP_INTERVAL = 60
Fri Jul 2 12:13:59 UTC 2021 MustGather>> TOP_DASH_H_INTERVAL = 5
Fri Jul 2 12:13:59 UTC 2021 MustGather>> VMSTAT_INTERVAL = 5
Fri Jul 2 12:13:59 UTC 2021 MustGather>> Collecting user authority data...
Fri Jul 2 12:13:59 UTC 2021 MustGather>> Collection of user authority data complete.
Fri Jul 2 12:13:59 UTC 2021 MustGather>> Creating output files...
Fri Jul 2 12:13:59 UTC 2021 MustGather>> Output files created:
Fri Jul 2 12:13:59 UTC 2021 MustGather>>      vmstat.out
Fri Jul 2 12:13:59 UTC 2021 MustGather>>      ps.out
Fri Jul 2 12:13:59 UTC 2021 MustGather>>      top.out
Fri Jul 2 12:13:59 UTC 2021 MustGather>>      topdashH.1.out
Fri Jul 2 12:13:59 UTC 2021 MustGather>> Collecting the first netstat snapshot...
Fri Jul 2 12:13:59 UTC 2021 MustGather>> First netstat snapshot complete. 
...

```

## Access to the servlet which causes hang

Access to following URL by your browser

- http://localhost:9080/hang/index.html

Set sleep time and push submit button

![sleep-servlet](sleep-servlet.png)


## Check gathered logs

trapit gathers logs at /pdprof/share directory

- javacore*

- defaultServer.dump.zip

- linperf_RESULTS.tar.gz


## Log example

messages.log
```
[7/2/21, 12:30:59:965 UTC] 00000039 com.ibm.ws.request.timing.manager.SlowRequestManager         W TRAS0112W: Request AAAQG6QaJxc_AAAAAAAAAAA has been running on thread 0000002f for at least 5001.828ms. The following stack trace shows what this thread is currently running.

         at java.base/java.lang.Thread.sleep(Native Method)
         at java.base/java.lang.Thread.sleep(Thread.java:966)
         at pdpro.hang.MySleep.doSleep(MySleep.java:8)
         at pdpro.hang.MySleepServlet.doTask(MySleepServlet.java:44)
         at pdpro.hang.MySleepServlet.doPost(MySleepServlet.java:35)
         at javax.servlet.http.HttpServlet.service(HttpServlet.java:706)
         at javax.servlet.http.HttpServlet.service(HttpServlet.java:791)
         at com.ibm.ws.webcontainer.servlet.ServletWrapper.service(ServletWrapper.java:1258)
```


javacore*.txt
```
3XMTHREADINFO3           Java callstack:
4XESTACKTRACE                at java/lang/Thread.sleep(Native Method)
4XESTACKTRACE                at java/lang/Thread.sleep(Thread.java:966)
4XESTACKTRACE                at pdpro/hang/MySleep.doSleep(MySleep.java:8)
4XESTACKTRACE                at pdpro/hang/MySleepServlet.doTask(MySleepServlet.java:44)
4XESTACKTRACE                at pdpro/hang/MySleepServlet.doPost(MySleepServlet.java:35)
4XESTACKTRACE                at javax/servlet/http/HttpServlet.service(HttpServlet.java:706)
4XESTACKTRACE                at javax/servlet/http/HttpServlet.service(HttpServlet.java:791)
4XESTACKTRACE                at com/ibm/ws/webcontainer/servlet/ServletWrapper.service(ServletWrapper.java:1258)
```

Please check other document after you recreate the problem.
