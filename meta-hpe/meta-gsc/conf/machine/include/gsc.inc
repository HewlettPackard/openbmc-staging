require conf/machine/include/obmc-bsp-common.inc

# The Gen12 iLO ASIC is a Cortex A53 with the crypto extension
include conf/machine/include/arm/armv8a/tune-cortexa53.inc
DEFAULTTUNE = "cortexa53-crypto"

MACHINEOVERRIDES:append = ":gsc"

OBMC_COMPATIBLE_NAMES = "com.meta.Hardware.BMC.Model.HPEGen12"

# Flash size and offsets below are all in KB
FLASH_SIZE = "65536"
FLASH_UBOOT_OFFSET:flash-65536 = "1152"
FLASH_UBOOT_ENV_OFFSET:flash-65536 = "1536"
FLASH_KERNEL_OFFSET:flash-65536 = "1664"
FLASH_ROFS_OFFSET:flash-65536 = "11904"
FLASH_RWFS_OFFSET:flash-65536 = "61632"
FLASH_UBOOT_SIGNATURE_OFFSET:flash-65536 = "64960"

# U-Boot signing size in KB
FLASH_UBOOT_SIGNING_SIZE ?= "384"

# Configure the default U-Boot settings
PREFERRED_PROVIDER_virtual/bootloader = "u-boot-gsc"
PREFERRED_PROVIDER_u-boot = "u-boot-gsc"
PREFERRED_VERSION_u-boot-glider = "2025.01%"
PREFERRED_PROVIDER_u-boot-fw-utils ?= "u-boot-fw-utils-gsc"
UBOOT_MACHINE = "gsc_defconfig"
UBOOT_ENTRYPOINT = "0x00200000"

# Configure the default kernel settings
PREFERRED_PROVIDER_virtual/kernel ?= "linux-gsc"
PREFERRED_VERSION_linux-kernel ?= "6.1%"
KERNEL_CLASSES ?= "kernel-fitimage"
KERNEL_IMAGETYPE = "fitImage"
KERNEL_DEVICETREE = "hpe/gsc_b0.dtb"

# OpenBMC Machine Features used by HPE GSC Platforms
MACHINE_FEATURES += "\
        obmc-phosphor-fan-mgmt \
        obmc-phosphor-chassis-mgmt \
        obmc-phosphor-flash-mgmt \
        obmc-host-ipmi \
        obmc-host-state-mgmt \
        obmc-chassis-state-mgmt \
        obmc-bmc-state-mgmt \
        phosphor-led-manager \
        "

# Favor x86-power-control over phosphor state manager
VIRTUAL-RUNTIME_obmc-host-state-manager ?= "x86-power-control"
VIRTUAL-RUNTIME_obmc-chassis-state-manager ?= "x86-power-control"
VIRTUAL-RUNTIME_obmc-fan-control ?= "phosphor-pid-control"

# Configure the PREFERRED_PROVIDERS for HPE Packages
PREFERRED_PROVIDER_virtual/obmc-host-ipmi-hw = "phosphor-ipmi-kcs"
PREFERRED_PROVIDER_virtual/obmc-chassis-mgmt = "packagegroup-hpe-apps"
PREFERRED_PROVIDER_virtual/obmc-fan-mgmt = "packagegroup-hpe-apps"
PREFERRED_PROVIDER_virtual/obmc-flash-mgmt = "packagegroup-hpe-apps"
PREFERRED_PROVIDER_virtual/obmc-system-mgmt = "packagegroup-hpe-apps"

# Enable serial console on ttyS0
SERIAL_CONSOLES = "115200;ttyS0"
