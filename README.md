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
Installing OpenBMC on a HPE Proliant Gen12 server requires Transfer of Ownership, for more details please see https://www.hpe.com/us/en/compute/openbmc-proliant-servers.html.
