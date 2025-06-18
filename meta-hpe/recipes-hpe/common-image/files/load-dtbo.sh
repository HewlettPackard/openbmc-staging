#!/bin/sh

mkdir -p /sys/kernel/config/device-tree/overlays/dtoverlay
if [ -f "/boot/proliant_${BOARD}.dtbo" ]
then
	cat /boot/proliant_"${BOARD}".dtbo > /sys/kernel/config/device-tree/overlays/dtoverlay/dtbo
fi
