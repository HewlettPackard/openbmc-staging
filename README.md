HPE's OpenBMC Staging repository is an "out of tree" staging area for OpenBMC development, allowing for public testing and exploration of ideas that are not ready for submission to [OpenBMC](https://github.com/openbmc/openbmc). Contents of this repository are tested against the included OpenBMC submodule and may fail to build on newer commits from OpenBMC.

# How To Use
The "out of tree" nature of this repository requires some minor changes from a typical OpenBMC build. Examples shown below are for HPE's Gen12 servers which use the `gsc` machine type.

Clone the repository:
```bash
git clone --recursive https://github.com/HewlettPackard/openbmc-staging.git
```
Use the included `setup` script:
```bash
cd openbmc-staging
. setup gsc
```
Running the `setup` script will create a top level `build` directory to avoid contaminating the openbmc submodule.

Start the build:
```bash
bitbake obmc-phosphor-image
```
Once the build completes the build artifacts can be found in the top level build directory:
```bash
ls build/gsc/tmp/deploy/images/gsc/
```

# Run OpenBMC On Your HPE Proliant Gen12 Servers
Installing OpenBMC on a HPE Proliant Gen12 server for the first time requires Transfer of Ownership, for more details please see https://www.hpe.com/us/en/compute/openbmc-proliant-servers.html.  iLO 1.16 or later is needed for this process to work.

# Currently Supported Systems
This code has been primarily tested against DL340 Gen 12 Proliant systems.  The minimum BIOS version for a DL340 Gen12 is 1.52. Running with an earlier BIOS will result in a crash during host power operations.

# Recovering Back to iLO
There are several ways to recover your system back to iLO but all methods lead back to a recovery process which can take up to 30 minutes.  During recovery there is no feedback to the user.  Note: One a recovery is complete, a Transfer of Ownership process will need to begin again before OpenBMC can be used on the system.

1. Users can unplug the power and hold down the UID button within the first 30 seconds. Once the LED is flashing the button can be released and recovery will begin.
2. For advanced users, from a booted OpenBMC a user can write data to the SPI and will trigger a recovery process. Advanced users need to write to or erase 64kb at offset 0x3F70000 and power cycle.
