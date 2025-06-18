#!/bin/sh

if [ $# -ne 1 ]
then
    echo "usage: $0 <add|remove|connect|disconnect>" >&2
    exit 1
fi

action=$1
config="ncm"

gadget_name=vnic
gadget_dir=/sys/kernel/config/usb_gadget/$gadget_name

set -ex

case "$action" in
add)
    if [ -e "$gadget_dir" ]; then
            echo "Already started" >&2
            exit 0
    fi
    mkdir -p $gadget_dir
    (
    cd $gadget_dir
    # Use HPE vNIC vendor and product id
    echo "0x0001" > bcdDevice
    echo "0x0200" > bcdUSB
    echo "0x2cc8" > idVendor
    echo "0x2927" > idProduct
    echo "0x02"   > bDeviceClass
    echo "0x0d"   > bDeviceSubClass
    echo "0x00"   > bDeviceProtocol
    echo "0x40"   > bMaxPacketSize0
    # English language strings...
    mkdir -p strings/0x409
    #TODO revisit for OEM support in future
    echo "Hewlett Packard Enterprise" > strings/0x409/manufacturer
    echo "HPE Virtual NIC (NCM)" > strings/0x409/product
    if [ -e "/tmp/chipId" ]; then
        serial_num=`cat /tmp/chipId`
        if [ -z "${serial_num}" ]; then
    	    echo "VNIC0123456789" > strings/0x409/serialnumber
        else
    	    echo "VNIC${serial_num}"  > strings/0x409/serialnumber
        fi
    else
        echo "VNIC0123456789" > strings/0x409/serialnumber
    fi
    # create configuration
    mkdir -p configs/c.1
    mkdir -p configs/c.1/strings/0x409
    echo 250 > configs/c.1/MaxPower
    echo "0xE0" > configs/c.1/bmAttributes
    echo "USB CDC Ethernet config" > configs/c.1/strings/0x409/configuration
    # create CDC ECM/EEM/NCM/RNDIS config
    mkdir -p functions/${config}.usb0
    #TODO need to revisit Host MAC address
    echo "fa:4b:7f:fc:bc:02" > functions/${config}.usb0/host_addr
    echo "0a:ca:fe:f0:0d:04" > functions/${config}.usb0/dev_addr
    ln -s functions/${config}.usb0 configs/c.1
    )
    ;;
remove)
    if [ ! -e "$gadget_dir" ]; then
            echo "Not yet started" >&2
            exit 0
    fi
    (
    cd $gadget_dir
    if [ -n "`cat UDC`" ]; then
      networkctl down usb0
      echo "" > UDC
    fi
    sleep 1s
    rm configs/c.1/*.usb0
    rmdir functions/*.usb0
    rmdir configs/c.1/strings/0x409
    rmdir configs/c.1
    rmdir strings/0x409
    )
    rmdir $gadget_dir
    ;;
connect)
    if [ ! -e "$gadget_dir" ]; then
            echo "Not yet started" >&2
            exit 1
    fi
    (
    cd $gadget_dir
    if [ -z "`cat UDC`" ]; then
      echo "80405000.udc" > UDC
      sleep 1
      networkctl up usb0
    else
      echo "Already connected"
    fi
    )
    ;;
disconnect)
    if [ ! -e "$gadget_dir" ]; then
            echo "Not yet started" >&2
            exit 0
    fi
    (
    cd $gadget_dir
    if [ -n "`cat UDC`" ]; then
      networkctl down usb0
      echo "" > UDC
    fi
    )
    ;;
*)
    echo "invalid action $action" >&2
    exit 1
esac

exit 0
