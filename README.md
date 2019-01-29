小组成员：
王昊，1120171192；吴杰，1120171217  
小组分工：
王昊：视频信息流、视频录制、视频上传； 吴杰：视频播放  
文件目录如下：  
![Image text](https://github.com/jiecaojun/FinalExam/blob/master/%E6%96%87%E4%BB%B6%E7%9B%AE%E5%BD%95.png)  
APP完成了基本要求：包括  
1、访问API: http://10.108.10.39:8080/minidouyin/feed; 2、使用RecyclerView显示视频列表；3、使用Glide加载封面图；  
4、从视频信息流点击某个视频封面进入播放页面；5、根据视频信息的url播放视频；6、点击屏幕开始播放，结束后点击再次播放；    
7、预览摄像头内容；8、点击“录制”/”停止”按钮开始/停止录制；9、录像保存到文件里；  
10、POST http://10.108.10.39:8080/minidouyin/video； 11、首页点击”发布”按钮进入上传页面。选择视频文件，选择封
面图，上传视频；  
  
特殊功能：  
1、能够显示刷新出来的视频作者信息（姓名、学号）；2、双击视频点赞；3、在录制十秒后会自动停止；
4、视频录制结束后自动生成封面，并预览，点击"send a story"上传；5、大量使用动画，活动间的跳转，双击点赞，视频上传结果反馈等都使用了lottie动画；  
![Image text](https://github.com/jiecaojun/FinalExam/blob/master/doc/%E6%8F%90%E4%BA%A4%E6%88%90%E5%8A%9F.gif)
6、视频列表使用瀑布流布局；  
  
创新点：1、拥有平滑的切换效果，在视频列表点击后能够平滑的切换到播放界面；2、下拉刷新，滑动到列表最下端后上拉刷新；  

解决的最难的问题：在APP基本完成以后想要实现瀑布流布局，产生了很大的问题；  
