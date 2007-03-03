/*
 * Copyright (C) [2004, 2005, 2006], Hyperic, Inc.
 * This file is part of SIGAR.
 * 
 * SIGAR is free software; you can redistribute it and/or modify
 * it under the terms version 2 of the GNU General Public License as
 * published by the Free Software Foundation. This program is distributed
 * in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA.
 */

package org.hyperic.sigar.win32;

public class LocaleInfo extends Win32 {

    /**
     * Localized name of language
     */
    public static final int LOCALE_SLANGUAGE = 0x00000002;

    /**
     * English primary language id
     */
    public static final int LANG_ENGLISH = 0x09;

    private int id;

    private static native int getSystemDefaultLCID();

    private static native String getAttribute(int id, int attr);

    public LocaleInfo() {
        this(getSystemDefaultLCID());
    }

    public static final int makeLangId(int primary, int sub) {
        return (sub << 10) | primary;
    }

    public LocaleInfo(Integer id) {
        this(id.intValue());
    }

    public LocaleInfo(int id) {
        this.id = id;
    }

    public LocaleInfo(int primary, int sub) {
        this(makeLangId(primary, sub));
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrimaryLangId() {
        return this.id & 0x3ff;
    }

    public String getPerflibLangId() {
        String id =
            Integer.toHexString(getPrimaryLangId()).toUpperCase();

        //length always == 3
        int pad = 3 - id.length();
        StringBuffer fid = new StringBuffer(3);
        while (pad-- > 0) {
            fid.append("0");
        }
        fid.append(id);

        return fid.toString();
    }

    public int getSubLangId() {
        return this.id >> 10;
    }

    public String getAttribute(int attr) {
        return getAttribute(this.id, attr);
    }

    public String getLocalizedLanguageName() {
        return getAttribute(LOCALE_SLANGUAGE);
    }

    public String toString() {
        return getId() + ":" + getLocalizedLanguageName();
    }
}