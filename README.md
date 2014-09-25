Welcome to the ImageScrollViewExample wiki!
# Description
This project is example of ImageScrollView using.
ImageScrollView is ScrollView with ONLY Top Scretchable (or Cropped) Image for info screens where developer places photo, phones, mails and other details about contact.
# How to
Develeper must initialize ImageScrollView, ImageView and must call function ImageScrollView.setResizableImage(ImageView).
So you can call ImageScrollView.setEnableAnimation(boolean) for on/off animation.
Attention! Your ImageView must be at top of ScrollView (internal layout), has scaleType = "centerCrop" and layoutWidth = "match_parent"
# Building in eclipse
Project use android-support-v7-appcompat. So after project downloading from repository to your workspace you must set path to this library (right click to project -> Properties -> Android in window left part -> Set library in window right part).
# Enviroment
Android 3.0 and higher. Android low 3.0 not tested.
