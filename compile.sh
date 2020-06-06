clear
./s4 s4r -a=framework.FrameWorkApp -b=`pwd`/build.gradle myApp
./s4 deploy -s4r=`pwd`/build/libs/myApp.s4r -c=cluster1 -appName=myApp
