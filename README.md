Welcome to the AvatarScrollViewExample wiki!
# Description
This project is example of AvatarScrollView using.
AvatarScrollView is ScrollView with ONLY Top Scretchable (or Cropped) Image for info screens where developer places photo, phones, mails and other details about contact.

***

![1](http://cs623426.vk.me/v623426629/3486/Kmi9ormNs1U.jpg)

***

![2](http://cs623426.vk.me/v623426629/348f/6akn0sipSPI.jpg)

***

![3](http://cs623426.vk.me/v623426629/3498/R0N5JjULJCI.jpg)

***

![4](http://cs623426.vk.me/v623426629/34a1/oAzZskcqrbQ.jpg)

***

# How to
Develeper must initialize AvatarScrollView , ImageView and must call function AvatarScrollView .setResizableImage(ImageView).     
So you can call AvatarScrollView.enableAnimation(boolean) for on/off animation.     
Attention! Your ImageView must be at top of ScrollView (internal layout), has scaleType = "centerCrop" and layoutWidth = "match_parent".     
Please use "master" branch.
# Building in eclipse
Project use android-support-v7-appcompat. So after project downloading from repository to your workspace you must set path to this library (right click to project -> Properties -> Android in window left part -> Set library in window right part).
# Enviroment
Android 3.0 and higher. Android low 3.0 not tested.
