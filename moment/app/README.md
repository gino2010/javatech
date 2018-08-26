# Moment App

Android project for interview exam.

好久不写android程序，写的有点吃力。很多技术认知还停留存在4年多前的样子，正好更新一下。

I haven't written the android program for a long time, and it's a bit hard to write. A lot of technical cognition still stays like it was more than four years ago. Just upgrade it.

程序后端要求图片binary方式存入数据库，然后前端通过请求HTTP接口获得图片数据，这个感觉好传统的处理方式，不太合理。

- 对数据库压力大，可以考虑类似OSS相关服务
- 无法使用CDN，无法提供较好的网络响应

The backend of the program requires the image binary to be stored in the database, and then the front end obtains the image data through the HTTP request interface. I feel this processing method is so  traditional and not reasonable.

- Overload on the database, could consider similar OSS related services
- Unable to use CDN, unable to provide good network response

如果我去实现肯定不用这个思路

If I implement this project, I definitely don't use this tech architecture

### Tech Tips

- ButterKnife
- Picasso
- OKHttp
- Recycle View

### Environment
- mac os x 10.13.6
- android studio 3.1.4
- android 7.1.1

### Note
程序的实际布局和设计稿还是有点细节区别，这个需要在研究一下

There is still a little different between app layout and design layout. I need to study more.

由于使用Base64的图片传输方式，所以重新定义了Picasso的Downloader，便于Base64解码。

Due to the use of Base64's image format, I redefined Picasso's Downloader to facilitate Base64 decoding.

之所以这样，可能是因为出题者就想考验相关技术

The reason for this may be because the examiner wants to examine the relevant technology.

screenshot目录中有截图