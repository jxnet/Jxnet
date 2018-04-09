/**
 * Copyright (C) 2017-2018  Ardika Rommy Sanjaya <contact@ardikars.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.ardikars.jxnet;

import com.ardikars.jxnet.exception.PlatformNotSupportedException;
import com.ardikars.jxnet.util.Library;
import com.ardikars.jxnet.util.Platforms;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class DynamicLibrary implements Library.Loader {

    private final Logger logger = Logger.getLogger(DynamicLibrary.class.getName());

    @Override
    public void load() {
        switch (Platforms.getName()) {
            case LINUX:
                if (Platforms.isArm()) {
                    if ("v7".equals(Platforms.getVersion()) || "v6".equals(Platforms.getVersion())) {
                        try {
                            Library.loadLibrary(Library.DYNAMIC_LINUX_ARM32);
                        } catch (IllegalArgumentException e) {
                            logger.warning(e.getMessage());
                        } catch (IOException e) {
                            logger.warning(e.getMessage());
                        }
                    }
                } else {
                    if (Platforms.is64Bit()) {
                        try {
                            Library.loadLibrary(Library.DYNAMIC_LINUX_X64);
                        } catch (IllegalArgumentException e) {
                            logger.warning(e.getMessage());
                        } catch (IOException e) {
                            logger.warning(e.getMessage());
                        }
                    } else {
                        try {
                            Library.loadLibrary(Library.DYNAMIC_LINUX_X86);
                        } catch (IllegalArgumentException e) {
                            logger.warning(e.getMessage());
                        } catch (IOException e) {
                            logger.warning(e.getMessage());
                        }
                    }
                }
                break;
            case WINDOWS:
                if (new File(Library.NPCAP_DLL).exists())  {
                    System.load(Library.NPCAP_DLL);
                    loadDynamicWindowsLibrary();
                } else if (new File(Library.WPCAP_DLL).exists()) {
                    System.load(Library.WPCAP_DLL);
                    loadDynamicWindowsLibrary();
                } else {
                    throw new UnsatisfiedLinkError("Npcap or Winpcap is not installed yet.");
                }
                break;
            case FREEBSD:
                if (Platforms.is64Bit()) {
                    try {
                        Library.loadLibrary(Library.DYNAMIC_FREEBSD_X64);
                    } catch (IllegalArgumentException e) {
                        logger.warning(e.getMessage());
                    } catch (IOException e) {
                        logger.warning(e.getMessage());
                    }
                } else {
                    try {
                        Library.loadLibrary(Library.DYNAMIC_FREEBSD_X86);
                    } catch (IllegalArgumentException e) {
                        logger.warning(e.getMessage());
                    } catch (IOException e) {
                        logger.warning(e.getMessage());
                    }
                }
                break;
            case DARWIN:
                if (Platforms.is64Bit()) {
                    try {
                        Library.loadLibrary(Library.DYNAMIC_DARWIN_X64);
                    } catch (IllegalArgumentException e) {
                        logger.warning(e.getMessage());
                    } catch (IOException e) {
                        logger.warning(e.getMessage());
                    }
                }
                break;
            default:
                throw new PlatformNotSupportedException("Your platform does't supported by dynamic jxnet.");
        }
    }

    private void loadDynamicWindowsLibrary() {
        if (Platforms.is64Bit()) {
            try {
                Library.loadLibrary(Library.DYNAMIC_WINDOWS_X64);
            } catch (IllegalArgumentException e) {
                logger.warning(e.getMessage());
            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
        } else {
            try {
                Library.loadLibrary(Library.DYNAMIC_WINDOWS_X86);
            } catch (IllegalArgumentException e) {
                logger.warning(e.getMessage());
            } catch (IOException e) {
                logger.warning(e.getMessage());
            }
        }
    }

}
