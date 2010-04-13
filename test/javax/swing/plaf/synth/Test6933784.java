/*
 * Copyright 2010 Sun Microsystems, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Sun Microsystems, Inc., 4150 Network Circle, Santa Clara,
 * CA 95054 USA or visit www.sun.com if you need additional information or
 * have any questions.
 */

/* @test
   @bug 6933784
   @summary NIMBUS: ImageView getNoImageIcon and getLoadingImageIcon returns nulls instead of an icon
   @author Pavel Porvatov
   @run main Test6933784
*/

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.plaf.synth.SynthLookAndFeel;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.ImageView;
import java.io.StringReader;

public class Test6933784 {
    public static void main(String[] args) throws Exception {
        UIManager.setLookAndFeel(new SynthLookAndFeel());

        checkImages();

        UIManager.setLookAndFeel(new NimbusLookAndFeel());

        checkImages();
    }

    private static void checkImages() throws Exception {
        SwingUtilities.invokeAndWait(new Runnable() {
            public void run() {
                HTMLEditorKit c = new HTMLEditorKit();
                HTMLDocument doc = new HTMLDocument();

                try {
                    c.read(new StringReader("<HTML><TITLE>Test</TITLE><BODY><IMG id=test></BODY></HTML>"), doc, 0);
                } catch (Exception e) {
                    throw new RuntimeException("The test failed", e);
                }

                Element elem = doc.getElement("test");
                ImageView iv = new ImageView(elem);

                if (iv.getLoadingImageIcon() == null) {
                    throw new RuntimeException("getLoadingImageIcon returns null");
                }

                if (iv.getNoImageIcon() == null) {
                    throw new RuntimeException("getNoImageIcon returns null");
                }
            }
        });
    }
}
