SUMMARY = "OpenBMC for HPE - Applications"
PR = "r1"

inherit packagegroup

PROVIDES = "${PACKAGES}"
PACKAGES = " \
        ${PN}-chassis \
        ${PN}-fans \
        ${PN}-flash \
        ${PN}-system \
        "

PROVIDES += "virtual/obmc-chassis-mgmt"
PROVIDES += "virtual/obmc-fan-mgmt"
PROVIDES += "virtual/obmc-flash-mgmt"
PROVIDES += "virtual/obmc-system-mgmt"

RPROVIDES:${PN}-chassis += "virtual-obmc-chassis-mgmt"
RPROVIDES:${PN}-fans += "virtual-obmc-fan-mgmt"
RPROVIDES:${PN}-flash += "virtual-obmc-flash-mgmt"
RPROVIDES:${PN}-system += "virtual-obmc-system-mgmt"

SUMMARY:${PN}-fans = "HPE Fans"
RDEPENDS:${PN}-fans = " \
        phosphor-pid-control \
        "

SUMMARY:${PN}-flash = "HPE Flash"
RDEPENDS:${PN}-flash = " \
        "

SUMMARY:${PN}-system = "HPE System"
RDEPENDS:${PN}-system = " \
        bmcweb \
        webui-vue \
        dbus-sensors \
        phosphor-ipmi-host \
        ipmitool \
        entity-manager \
        host-ehci-owner-reset \
        "


RDEPENDS:${PN}-logging = " phosphor-logging"


RDEPENDS:${PN}-fans = " \
        phosphor-pid-control \
        "
RDEPENDS:${PN}-flash = " \
        phosphor-software-manager \
        phosphor-software-manager-version \
        phosphor-software-manager-updater \
    "

RDEPENDS:${PN}-chassis = " \
        obmc-host-failure-reboots \
        x86-power-control \
        "

RDEPENDS:${PN}-fan-control = " \
         ${VIRTUAL-RUNTIME_obmc-fan-control} \
         "


RDEPENDS:${PN}-system:append = " \
    phosphor-ipmi-fru \
    phosphor-ipmi-net \
    phosphor-sel-logger \
    phosphor-post-code-manager \
    phosphor-host-postd \
    phosphor-image-signing \
    phosphor-state-manager \
    phosphor-certificate-manager \
    phosphor-user-manager \
    phosphor-inventory-manager \
    phosphor-hwmon \
    smbios-mdr \
    srvcfg-manager \
    rsyslog \
    pldm \
    libmctp \
    openssl-bin \
    openssl-engines \
    proliant-support \
    gxp-dbus-sensors \
    common-image \
    vnic \
    host-boot-enable \
    chif \
    safs-vrom \
    "
