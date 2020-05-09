#!/usr/bin/python

###########################################################################################

HLOCAL = '0123456789ABCDEFGHIJKLMNOPQRSTUV'
HKEY = '92e770c4-ea01-4cc6-9d84-cadc2d7b327e'
SUFFIX = 'icp'

###########################################################################################

import re
import os
import sys
import hashlib
import base64
import zipfile
import tempfile
from arc4 import ARC4

def is_license_file(cls):
    return cls.find(HKEY.encode()) >= 0

def set_hash(cls, email):
    m = hashlib.md5(email.encode())
    dat = m.hexdigest()
    return cls.replace(HLOCAL.encode(), dat.encode())

def get_info(email):
    arc = ARC4(HKEY)
    return base64.b64encode(arc.encrypt(email))

def make_zip(zin, out, filename, license, info):
    tmpfd, tmpname = tempfile.mkstemp(dir=os.path.dirname(jar))
    os.close(tmpfd)
    with zipfile.ZipFile(tmpname, 'w') as zout:
        for item in zin.infolist():
            if item.filename != filename:
                zout.writestr(item, zin.read(item.filename))
    os.rename(tmpname, out)
    with zipfile.ZipFile(out, mode='a', compression=zipfile.ZIP_DEFLATED) as zf:
        zf.writestr("info", info)
        zf.writestr(filename, license)


if (len(sys.argv) < 4):
    print("Usage: {} <name> <jar> <email>".format(sys.argv[0]))
    exit()

name = sys.argv[1]
jar = sys.argv[2]
email = sys.argv[3]
output = "{}.{}".format(name, SUFFIX)

with zipfile.ZipFile(jar, 'r') as zin:
    names = list(filter(lambda f: f.endswith('.class'), zin.namelist()))
    for nm in names:
        cls = zin.read(nm)
        if not is_license_file(cls):
            continue
        info = get_info(email)
        cls = set_hash(cls, email)
        make_zip(zin, output, nm, cls, info)
        break

