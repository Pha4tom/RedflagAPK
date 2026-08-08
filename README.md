app to decompile and analyze APKS uses apktool,jadx and unflutter

lots of stuff to install I know but it's worth it

Installation

Requirements

- Python 3
- Git
- Android SDK / platform tools
- Java
- "Apktool" (https://github.com/iBotPeaches/Apktool)
- "JADX" (https://github.com/skylot/jadx)
- Flutter analysis tools used by RedflagAPK

Clone the repository

git clone https://github.com/Pha4tom/RedflagAPK.git
cd RedflagAPK

Install Python dependencies

pip install -r requirements.txt

Install Apktool

Make sure "apktool" is available in your "PATH".

Check:

apktool --version

Install JADX

Make sure "jadx" is available in your "PATH".

Check:

jadx --version

Flutter support

RedflagAPK can detect Flutter APKs and analyze their native Flutter binaries.

For Flutter AOT analysis, make sure the required Flutter analysis tools are installed and available to RedflagAPK.

Verify the Flutter tooling:

flutter --version

If Flutter is installed correctly, RedflagAPK will automatically detect Flutter applications during a scan.

Verify the installation

Run:

python triage.py --help

If the help menu appears, RedflagAPK is ready to use.
