ImgurWallpaper
==============
Downloads wallpapers from imgur galleryIdentifiers in bulk.  Wallpapers are downloaded to a "walls" folder in your "My Pictures"
folder.

ProTip:  Check "Download all" and watch the magic.  You can do this everytime and it will only download new/missing wallpapers

##Run

###Windows
```
.\gradlew.bat build
java -jar build\libs\ImgurWallpaper.jar
```
or
```
.\gradlew.bat run
```

###Linux/Mac
```
./gradlew build
java -jar build/libs/ImgurWallpaper.jar
```
or
```
./gradlew run
```

####TODO
1. Multi-threaded downloading
2. Duplicate image detection