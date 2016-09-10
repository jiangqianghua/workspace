#########################################################################
# File Name: build_android.sh
# Author: qianghua jiang
# mail: 240437339@qq.com
# Created Time: Sat 02 Jul 2016 07:31:08 PM PDT
#########################################################################
#!/bin/bash 
NDK=/home/jiangqianghua/Desktop/ffmpeg/ffmpeg/ffmpeg-2.7.1/ndk/android-ndk-r9d
SYSROOT=$NDK/platforms/android-9/arch-arm/
TOOLCHAIN=$NDK/toolchains/arm-linux-androideabi-4.8/prebuilt/linux-x86_64
  
function build_one  
{  
./configure \
    --prefix=$PREFIX \
    --enable-shared \
    --disable-static \
    --disable-doc \
    --disable-ffserver \
    --enable-cross-compile \
    --cross-prefix=$TOOLCHAIN/bin/arm-linux-androideabi- \
    --target-os=linux \
    --arch=arm  \
    --sysroot=$SYSROOT \
    --extra-cflags="-Os -fpic $ADDI_CFLAGS" \
    --extra-ldflags="$ADDI_LDFLAGS" \
    $ADDITIONAL_CONFIGURE_FLAG  
}  
CPU=arm
PREFIX=$(pwd)/android/$CPU 
ADDI_CFLAGS="-marm"  
build_one

