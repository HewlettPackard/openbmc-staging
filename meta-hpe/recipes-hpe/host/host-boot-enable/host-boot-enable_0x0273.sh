#!/bin/sh

echo 'Setup ubm'
# Now we need to find the various ubm mux
# UBM are attached to I2C bus
# and must be declared into the kernel DTS which will create
# entries and name
mkdir -p /tmp/ubm
> /tmp/ubm/ubm_mux.txt
> /tmp/ubm/ubm_map.txt

# Identify the UBM devices on the Mux to
# turn them on/off during host bring up/down phases
root="/sys/devices/platform/ahb@80000000/"
list_device=`find $root -name 'name'`
for i in $list_device
do
        name=`cat $i`
        if [ "$name" == "ubm4" ]
        then
                mux=`echo $i | awk -F/ '{ print $8 }'`
                mux=`echo $mux | sed 's/i2c-//'`
                echo $mux >> /tmp/ubm/ubm_mux.txt
        fi
done

# Identify all software muxes to create a mapping table for CHIF setup
# through the ROM.
# Linux kernel remaps the device when attached to a software mux
# Format is
# CPLD Register index / Register value / i2c bus
# Identify all software muxes to create a mapping table for CHIF setup
# through the ROM.
# Linux kernel remaps the device when attached to a software mux
# Format is:
# CPLD Register index (hex) / Register value (dec) / i2c bus (dec)
muxId=132
for bus in $(seq 0 20)
do
        busname="/sys/devices/platform/ahb@80000000/*i2cmux@${bus}"
        if [ -e ${busname} ]
        then
                for ch in $(seq 1 16)
                do
                        cname="${busname}/channel-${ch}"
                        if [ -e ${cname} ]
                        then
                                target=`ls -l ${cname} | awk '{ print $11}' | awk -F"/" '{ print $4}' | sed 's/i2c-//' `
                                muxHex=`printf "%x" $muxId`
                                echo $muxHex $ch $target >> /tmp/ubm/ubm_map.txt
                        fi
                done
        fi
        muxId=`expr $muxId + 1`
done

echo 'Setup chif'
/usr/bin/hpe-publish-uefi-version
/usr/bin/hpe-platdef-extract
systemctl restart xyz.openbmc_project.GxpChif.service

# create staging directory for upcoming rom
mkdir -p /tmp/stage
