insert into SNIPPET values (10000, 1, '[SWT]SnippetSnippet1.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * example snippet: Hello World
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.widgets.*;

public class Snippet1 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell(display);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10001, 1, '[SWT]SnippetSnippet10.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Draw using transformations, paths and alpha blending
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet10 {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Advanced Graphics");
		FontData fd = shell.getFont().getFontData()[0];
		final Font font = new Font(display, fd.getName(), 60, SWT.BOLD | SWT.ITALIC);
		final Image image = new Image(display, 640, 480);
		final Rectangle rect = image.getBounds();
		GC gc = new GC(image);
		gc.setBackground(display.getSystemColor(SWT.COLOR_RED));
		gc.fillOval(rect.x, rect.y, rect.width, rect.height);
		gc.dispose();
		shell.addListener(SWT.Paint, new Listener() {
			public void handleEvent(Event event) {
				GC gc = event.gc;				
				Transform tr = new Transform(display);
				tr.translate(50, 120);
				tr.rotate(-30);
				gc.drawImage(image, 0, 0, rect.width, rect.height, 0, 0, rect.width / 2, rect.height / 2);
				gc.setAlpha(100);
				gc.setTransform(tr);
				Path path = new Path(display);
				path.addString("SWT", 0, 0, font);
				gc.setBackground(display.getSystemColor(SWT.COLOR_GREEN));
				gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
				gc.fillPath(path);
				gc.drawPath(path);
				tr.dispose();
				path.dispose();
			}			
		});
		shell.setSize(shell.computeSize(rect.width / 2, rect.height / 2));
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		image.dispose();
		font.dispose();
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10002, 1, '[SWT]SnippetSnippet100.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Font example snippet: create a large font for use by a text widget
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet100 {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setBounds(10, 10, 200, 200);
	Text text = new Text(shell, SWT.MULTI);
	text.setBounds(10, 10, 150, 150);
	Font initialFont = text.getFont();
	FontData[] fontData = initialFont.getFontData();
	for (int i = 0; i < fontData.length; i++) {
		fontData[i].setHeight(24);
	}
	Font newFont = new Font(display, fontData);
	text.setFont(newFont);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	newFont.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10003, 1, '[SWT]SnippetSnippet101.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table example snippet: insert a table item (at an index)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet101 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Table table = new Table (shell, SWT.BORDER | SWT.MULTI);
	table.setSize (200, 200);
	for (int i=0; i<12; i++) {
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText ("Item " + i);
	}
	TableItem item = new TableItem (table, SWT.NONE, 1);
	item.setText ("*** New Item " + table.indexOf (item) + " ***");
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

}

', now(), now());
insert into SNIPPET values (10004, 1, '[SWT]SnippetSnippet102.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tree example snippet: insert a tree item (at an index)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet102 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Tree tree = new Tree (shell, SWT.BORDER | SWT.MULTI);
	tree.setSize (200, 200);
	for (int i=0; i<12; i++) {
		TreeItem item = new TreeItem (tree, SWT.NONE);
		item.setText ("Item " + i);
	}
	TreeItem item = new TreeItem (tree, SWT.NONE, 1);
	TreeItem [] items = tree.getItems ();
	int index = 0;
	while (index < items.length && items [index] != item) index++;
	item.setText ("*** New Item " + index + " ***");
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

}
', now(), now());
insert into SNIPPET values (10005, 1, '[SWT]SnippetSnippet103.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table example snippet: update table item text
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet103 {

static char content = a;
public static void main(String[] args) {
	final Display display = new Display();
	Shell shell = new Shell(display);
	shell.setBounds(10, 10, 200, 240);
	Table table = new Table(shell, SWT.NONE);
	table.setBounds(10, 10, 160, 160);

	final TableItem[] items = new TableItem[4];
	for (int i = 0; i < 4; i++) {
		new TableColumn(table, SWT.NONE).setWidth(40);
	}
	for (int i = 0; i < 4; i++) {
		items[i] = new TableItem(table, SWT.NONE);
		populateItem(items[i]);
	}

	Button button = new Button(shell, SWT.PUSH);
	button.setText("Change");
	button.pack();
	button.setLocation(10, 180);
	button.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event event) {
			for (int i = 0; i < 4; i++) {
				populateItem(items[i]);
			}
		}
	});

	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}

static void populateItem(TableItem item) {
	String stringContent = String.valueOf(content);
	item.setText(
		new String[] {
			stringContent,
			stringContent,
			stringContent,
			stringContent });
	content++;
	if (content > z) content = a;
}

}
', now(), now());
insert into SNIPPET values (10006, 1, '[SWT]SnippetSnippet104.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Shell example snippet: create a splash screen
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet104 {

public static void main(String[] args) {
	final Display display = new Display();
	final int [] count = new int [] {4};
	final Image image = new Image(display, 300, 300);
	GC gc = new GC(image);
	gc.setBackground(display.getSystemColor(SWT.COLOR_CYAN));
	gc.fillRectangle(image.getBounds());
	gc.drawText("Splash Screen", 10, 10);
	gc.dispose();
	final Shell splash = new Shell(SWT.ON_TOP);
	final ProgressBar bar = new ProgressBar(splash, SWT.NONE);
	bar.setMaximum(count[0]);
	Label label = new Label(splash, SWT.NONE);
	label.setImage(image);
	FormLayout layout = new FormLayout();
	splash.setLayout(layout);
	FormData labelData = new FormData ();
	labelData.right = new FormAttachment (100, 0);
	labelData.bottom = new FormAttachment (100, 0);
	label.setLayoutData(labelData);
	FormData progressData = new FormData ();
	progressData.left = new FormAttachment (0, 5);
	progressData.right = new FormAttachment (100, -5);
	progressData.bottom = new FormAttachment (100, -5);
	bar.setLayoutData(progressData);
	splash.pack();
	Rectangle splashRect = splash.getBounds();
	Rectangle displayRect = display.getBounds();
	int x = (displayRect.width - splashRect.width) / 2;
	int y = (displayRect.height - splashRect.height) / 2;
	splash.setLocation(x, y);
	splash.open();
	display.asyncExec(new Runnable() {
		public void run() {
			Shell [] shells = new Shell[count[0]];
			for (int i=0; i<count[0]; i++) {
				shells [i] = new Shell(display);
				shells [i].setSize (300, 300);
				shells [i].addListener(SWT.Close, new Listener() {
					public void handleEvent (Event e) {
						--count[0];
					}
				});
				bar.setSelection(i+1);
				try {Thread.sleep(1000);} catch (Throwable e) {}
			}
			splash.close();
			image.dispose();
			for (int i=0; i<count[0]; i++) {
				shells [i].open();
			}
		}
	});
	while (count [0] != 0) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose();
}

}
', now(), now());
insert into SNIPPET values (10007, 1, '[SWT]SnippetSnippet105.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Program example snippet: invoke an external batch file
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.program.*;
import org.eclipse.swt.widgets.*;

public class Snippet105 {

public static void main(String[] args) {
	Display display = new Display ();
	Program.launch("c:\\cygwin\\cygwin.bat");
	display.dispose ();
}

}
', now(), now());
insert into SNIPPET values (10008, 1, '[SWT]SnippetSnippet106.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table example snippet: insert a table column (at an index)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet106 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new RowLayout (SWT.VERTICAL));
	final Table table = new Table (shell, SWT.BORDER | SWT.MULTI);
	table.setHeaderVisible (true);
	for (int i=0; i<4; i++) {
		TableColumn column = new TableColumn (table, SWT.NONE);
		column.setText ("Column " + i);
	}
	final TableColumn [] columns = table.getColumns ();
	for (int i=0; i<12; i++) {
		TableItem item = new TableItem (table, SWT.NONE);
		for (int j=0; j<columns.length; j++) {
			item.setText (j, "Item " + i);
		}
	}
	for (int i=0; i<columns.length; i++) columns [i].pack ();
	Button button = new Button (shell, SWT.PUSH);
	final int index = 1;
	button.setText ("Insert Column " + index + "a");
	button.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			TableColumn column = new TableColumn (table, SWT.NONE, index);
			column.setText ("Column " + index + "a");
			TableItem [] items = table.getItems ();
			for (int i=0; i<items.length; i++) {
				items [i].setText (index, "Item " + i + "a");
			}
			column.pack ();
		}
	});
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

}
', now(), now());
insert into SNIPPET values (10009, 1, '[SWT]SnippetSnippet107.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
/*
 * Sash example snippet: implement a simple splitter (with a 20 pixel limit)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet107 {

public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	Button button1 = new Button (shell, SWT.PUSH);
	button1.setText ("Button 1");
	final Sash sash = new Sash (shell, SWT.VERTICAL);
	Button button2 = new Button (shell, SWT.PUSH);
	button2.setText ("Button 2");
	
	final FormLayout form = new FormLayout ();
	shell.setLayout (form);
	
	FormData button1Data = new FormData ();
	button1Data.left = new FormAttachment (0, 0);
	button1Data.right = new FormAttachment (sash, 0);
	button1Data.top = new FormAttachment (0, 0);
	button1Data.bottom = new FormAttachment (100, 0);
	button1.setLayoutData (button1Data);

	final int limit = 20, percent = 50;
	final FormData sashData = new FormData ();
	sashData.left = new FormAttachment (percent, 0);
	sashData.top = new FormAttachment (0, 0);
	sashData.bottom = new FormAttachment (100, 0);
	sash.setLayoutData (sashData);
	sash.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			Rectangle sashRect = sash.getBounds ();
			Rectangle shellRect = shell.getClientArea ();
			int right = shellRect.width - sashRect.width - limit;
			e.x = Math.max (Math.min (e.x, right), limit);
			if (e.x != sashRect.x)  {
				sashData.left = new FormAttachment (0, e.x);
				shell.layout ();
			}
		}
	});
	
	FormData button2Data = new FormData ();
	button2Data.left = new FormAttachment (sash, 0);
	button2Data.right = new FormAttachment (100, 0);
	button2Data.top = new FormAttachment (0, 0);
	button2Data.bottom = new FormAttachment (100, 0);
	button2.setLayoutData (button2Data);
	
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10010, 1, '[SWT]SnippetSnippet108.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Button example snippet: set the default button
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;

public class Snippet108 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Label label = new Label (shell, SWT.NONE);
	label.setText ("Enter your name:");
	Text text = new Text (shell, SWT.BORDER);
	text.setLayoutData (new RowData (100, SWT.DEFAULT));
	Button ok = new Button (shell, SWT.PUSH);
	ok.setText ("OK");
	ok.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			System.out.println("OK");
		}
	});
	Button cancel = new Button (shell, SWT.PUSH);
	cancel.setText ("Cancel");
	cancel.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			System.out.println("Cancel");
		}
	});
	shell.setDefaultButton (cancel);
	shell.setLayout (new RowLayout ());
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10011, 1, '[SWT]SnippetSnippet109.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * SashForm example snippet: create a sash form with three children
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet109 {

public static void main (String [] args) {
	final Display display = new Display ();
	Shell shell = new Shell(display);
	shell.setLayout (new FillLayout());

	SashForm form = new SashForm(shell,SWT.HORIZONTAL);
	form.setLayout(new FillLayout());
	
	Composite child1 = new Composite(form,SWT.NONE);
	child1.setLayout(new FillLayout());
	new Label(child1,SWT.NONE).setText("Label in pane 1");
	
	Composite child2 = new Composite(form,SWT.NONE);
	child2.setLayout(new FillLayout());
	new Button(child2,SWT.PUSH).setText("Button in pane2");

	Composite child3 = new Composite(form,SWT.NONE);
	child3.setLayout(new FillLayout());
	new Label(child3,SWT.PUSH).setText("Label in pane3");
	
	form.setWeights(new int[] {30,40,30});
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10012, 1, '[SWT]SnippetSnippet11.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Text example snippet: set the selection (i-beam)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet11 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Text text = new Text (shell, SWT.BORDER | SWT.V_SCROLL);
	text.setBounds (10, 10, 100, 100);
	for (int i=0; i<16; i++) {
		text.append ("Line " + i + "\n");
	}
	shell.open ();
	text.setSelection (30);
	System.out.println ("selection=" + text.getSelection ());
	System.out.println ("caret position=" + text.getCaretPosition ());
	System.out.println ("caret location=" + text.getCaretLocation ());
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10013, 1, '[SWT]SnippetSnippet110.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table example snippet: find a table cell from mouse down (works for any table style)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

public class Snippet110 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	final Table table = new Table (shell, SWT.BORDER | SWT.V_SCROLL);
	table.setHeaderVisible (true);
	table.setLinesVisible (true);
	final int rowCount = 64, columnCount = 4;
	for (int i=0; i < columnCount; i++) {
		TableColumn column = new TableColumn (table, SWT.NONE);
		column.setText ("Column " + i);
	}
	for (int i=0; i < rowCount; i++) {
		TableItem item = new TableItem (table, SWT.NONE);
		for (int j=0; j < columnCount; j++) {
			item.setText (j, "Item " + i + "-" + j);
		}
	}
	for (int i=0; i < columnCount; i++) {
		table.getColumn (i).pack ();
	}
	Point size = table.computeSize (SWT.DEFAULT, 200);
	table.setSize (size);
	shell.pack ();
	table.addListener (SWT.MouseDown, new Listener () {
		public void handleEvent (Event event) {
			Rectangle clientArea = table.getClientArea ();
			Point pt = new Point (event.x, event.y);
			int index = table.getTopIndex ();
			while (index < table.getItemCount ()) {
				boolean visible = false;
				TableItem item = table.getItem (index);
				for (int i=0; i < columnCount; i++) {
					Rectangle rect = item.getBounds (i);
					if (rect.contains (pt)) {
						System.out.println ("Item " + index + "-" + i);
					}
					if (!visible && rect.intersects (clientArea)) {
						visible = true;
					}
				}
				if (!visible) return;
				index++;
			}
		}
	});
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10014, 1, '[SWT]SnippetSnippet111.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * TreeEditor example snippet: edit the text of a tree item (in place, fancy)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.custom.*;

public class Snippet111 {

public static void main (String [] args) {
	final Display display = new Display ();
	final Color black = display.getSystemColor (SWT.COLOR_BLACK);
	Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	final Tree tree = new Tree (shell, SWT.BORDER);
	for (int i=0; i<16; i++) {
		TreeItem itemI = new TreeItem (tree, SWT.NONE);
		itemI.setText ("Item " + i);
		for (int j=0; j<16; j++) {
			TreeItem itemJ = new TreeItem (itemI, SWT.NONE);
			itemJ.setText ("Item " + j);
		}
	}
	final TreeItem [] lastItem = new TreeItem [1];
	final TreeEditor editor = new TreeEditor (tree);
	tree.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event event) {
			final TreeItem item = (TreeItem) event.item;
			if (item != null && item == lastItem [0]) {
				boolean showBorder = true;
				final Composite composite = new Composite (tree, SWT.NONE);
				if (showBorder) composite.setBackground (black);
				final Text text = new Text (composite, SWT.NONE);
				final int inset = showBorder ? 1 : 0;
				composite.addListener (SWT.Resize, new Listener () {
					public void handleEvent (Event e) {
						Rectangle rect = composite.getClientArea ();
						text.setBounds (rect.x + inset, rect.y + inset, rect.width - inset * 2, rect.height - inset * 2);
					}
				});
				Listener textListener = new Listener () {
					public void handleEvent (final Event e) {
						switch (e.type) {
							case SWT.FocusOut:
								item.setText (text.getText ());
								composite.dispose ();
								break;
							case SWT.Verify:
								String newText = text.getText ();
								String leftText = newText.substring (0, e.start);
								String rightText = newText.substring (e.end, newText.length ());
								GC gc = new GC (text);
								Point size = gc.textExtent (leftText + e.text + rightText);
								gc.dispose ();
								size = text.computeSize (size.x, SWT.DEFAULT);
								editor.horizontalAlignment = SWT.LEFT;
								Rectangle itemRect = item.getBounds (), rect = tree.getClientArea ();
								editor.minimumWidth = Math.max (size.x, itemRect.width) + inset * 2;
								int left = itemRect.x, right = rect.x + rect.width;
								editor.minimumWidth = Math.min (editor.minimumWidth, right - left);
								editor.minimumHeight = size.y + inset * 2;
								editor.layout ();
								break;
							case SWT.Traverse:
								switch (e.detail) {
									case SWT.TRAVERSE_RETURN:
										item.setText (text.getText ());
										//FALL THROUGH
									case SWT.TRAVERSE_ESCAPE:
										composite.dispose ();
										e.doit = false;
								}
								break;
						}
					}
				};
				text.addListener (SWT.FocusOut, textListener);
				text.addListener (SWT.Traverse, textListener);
				text.addListener (SWT.Verify, textListener);
				editor.setEditor (composite, item);
				text.setText (item.getText ());
				text.selectAll ();
				text.setFocus ();
			}
			lastItem [0] = item;
		}
	});
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10015, 1, '[SWT]SnippetSnippet112.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Image example snippet: display an image in a group
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet112 {

public static void main (String [] args) {
	Display display = new Display ();
	final Image image = new Image (display, 20, 20);
	Color color = display.getSystemColor (SWT.COLOR_RED);
	GC gc = new GC (image);
	gc.setBackground (color);
	gc.fillRectangle (image.getBounds ());
	gc.dispose ();

	Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	Group group = new Group (shell, SWT.NONE);
	group.setLayout (new FillLayout ());
	group.setText ("a square");
	Canvas canvas = new Canvas (group, SWT.NONE);
	canvas.addPaintListener (new PaintListener () {
		public void paintControl (PaintEvent e) {
			e.gc.drawImage (image, 0, 0);
		}
	});

	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ())
			display.sleep ();
	}
	image.dispose ();
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10016, 1, '[SWT]SnippetSnippet113.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Table example snippet: detect a selection or check event in a table (SWT.CHECK)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet113 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Table table = new Table (shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	for (int i=0; i<12; i++) {
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText ("Item " + i);
	}
	table.setSize (100, 100);
	table.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event event) {
			String string = event.detail == SWT.CHECK ? "Checked" : "Selected";
			System.out.println (event.item + " " + string);
		}
	});
	shell.setSize (200, 200);
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10017, 1, '[SWT]SnippetSnippet114.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Tree example snippet: detect a selection or check event in a tree (SWT.CHECK)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet114 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Tree tree = new Tree (shell, SWT.CHECK | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	for (int i=0; i<12; i++) {
		TreeItem item = new TreeItem (tree, SWT.NONE);
		item.setText ("Item " + i);
	}
	tree.setSize (100, 100);
	tree.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event event) {
			String string = event.detail == SWT.CHECK ? "Checked" : "Selected";
			System.out.println (event.item + " " + string);
		}
	});
	shell.setSize (200, 200);
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10018, 1, '[SWT]SnippetSnippet115.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Composite example snippet: force radio behavior on two different composites
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet115 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new RowLayout (SWT.VERTICAL));
	Composite c1 = new Composite (shell, SWT.BORDER | SWT.NO_RADIO_GROUP);
	c1.setLayout (new RowLayout ());
	Composite c2 = new Composite (shell, SWT.BORDER | SWT.NO_RADIO_GROUP);
	c2.setLayout (new RowLayout ());
	final Composite [] composites = new Composite [] {c1, c2};
	Listener radioGroup = new Listener () {
		public void handleEvent (Event event) {
			for (int i=0; i<composites.length; i++) {
				Composite composite = composites [i];
				Control [] children = composite.getChildren ();
				for (int j=0; j<children.length; j++) {
					Control child = children [j];
					if (child instanceof Button) {
						Button button = (Button) child;
						if ((button.getStyle () & SWT.RADIO) != 0) button.setSelection (false);
					}
				}
			}
			Button button = (Button) event.widget;
			button.setSelection (true);
		}
	};
	for (int i=0; i<4; i++) {
		Button button = new Button (c1, SWT.RADIO);
		button.setText ("Button " + i);
		button.addListener (SWT.Selection, radioGroup);
	}
	for (int i=0; i<4; i++) {
		Button button = new Button (c2, SWT.RADIO);
		button.setText ("Button " + (i + 4));
		button.addListener (SWT.Selection, radioGroup);
	}
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10019, 1, '[SWT]SnippetSnippet116.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Text example snippet: stop CR from going to the default button
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet116  {
public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new GridLayout());
	Text text = new Text(shell, SWT.SINGLE | SWT.BORDER);
	text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	text.setText("Here is some text");
	text.addSelectionListener(new SelectionAdapter() {
		public void widgetDefaultSelected(SelectionEvent e) {
			System.out.println("Text default selected (overrides default button)");
		}
	});
	text.addTraverseListener(new TraverseListener() {
		public void keyTraversed(TraverseEvent e) {
			if (e.detail == SWT.TRAVERSE_RETURN) {
				e.doit = false;
				e.detail = SWT.TRAVERSE_NONE;
			}
		}
	});
	Button button = new Button(shell, SWT.PUSH);
	button.setText("Ok");
	button.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			System.out.println("Button selected");
		}
	});
	shell.setDefaultButton(button);
	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10020, 1, '[SWT]SnippetSnippet117.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Text example snippet: add a select all menu item to the control
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet117  {
public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	final Text t = new Text(shell, SWT.BORDER | SWT.MULTI);
    t.setText ("here is some text to be selected");
	Menu bar = new Menu (shell, SWT.BAR);
	shell.setMenuBar (bar);
	MenuItem editItem = new MenuItem (bar, SWT.CASCADE);
	editItem.setText ("Edit");
	Menu submenu = new Menu (shell, SWT.DROP_DOWN);
	editItem.setMenu (submenu);

	MenuItem item = new MenuItem (submenu, SWT.PUSH);
	item.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			t.selectAll();
		}
	});
	item.setText ("Select &All\tCtrl+A");
	item.setAccelerator (SWT.MOD1 + A);
	
	// Note that as long as your application has not overridden 
	// the global accelerators for copy, paste, and cut 
	//(CTRL+C or CTRL+INSERT, CTRL+V or SHIFT+INSERT, and CTRL+X or SHIFT+DELETE)
	// these behaviours are already available by default.
	// If your application overrides these accelerators,
	// you will need to call Text.copy(), Text.paste() and Text.cut()
	// from the Selection callback for the accelerator when the 
	// text widget has focus.

	shell.setSize(200, 200);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10021, 1, '[SWT]SnippetSnippet118.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Cursor example snippet: create a color cursor from an image file
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet118 {

public static void main (String [] args) {
	final Display display = new Display();
	final Shell shell = new Shell(display);
	shell.setSize(150, 150);
	final Cursor[] cursor = new Cursor[1];
	Button button = new Button(shell, SWT.PUSH);
	button.setText("Change cursor");
	Point size = button.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	button.setSize(size);
	button.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event e) {
			FileDialog dialog = new FileDialog(shell);
			dialog.setFilterExtensions(new String[] {"*.ico", "*.gif", "*.*"});
			String name = dialog.open();
			if (name == null) return;
			ImageData image = new ImageData(name);
			Cursor oldCursor = cursor[0];
			cursor[0] = new Cursor(display, image, 0, 0);
			shell.setCursor(cursor[0]);
			if (oldCursor != null) oldCursor.dispose();
		}
	});
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	if (cursor[0] != null) cursor[0].dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10022, 1, '[SWT]SnippetSnippet119.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Cursor example snippet: create a color cursor from a source and a mask
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet119 {

static byte[] srcData = {
	(byte)0x11, (byte)0x11, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x11, (byte)0x11, (byte)0x11,
	(byte)0x11, (byte)0x10, (byte)0x00, (byte)0x01, (byte)0x10, (byte)0x00, (byte)0x01, (byte)0x11, 
	(byte)0x11, (byte)0x00, (byte)0x22, (byte)0x01, (byte)0x10, (byte)0x33, (byte)0x00, (byte)0x11,
	(byte)0x10, (byte)0x02, (byte)0x22, (byte)0x01, (byte)0x10, (byte)0x33, (byte)0x30, (byte)0x01,
	(byte)0x10, (byte)0x22, (byte)0x22, (byte)0x01, (byte)0x10, (byte)0x33, (byte)0x33, (byte)0x01,
	(byte)0x10, (byte)0x22, (byte)0x22, (byte)0x01, (byte)0x10, (byte)0x33, (byte)0x33, (byte)0x01,
	(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
	(byte)0x01, (byte)0x11, (byte)0x11, (byte)0x01, (byte)0x10, (byte)0x11, (byte)0x11, (byte)0x10,
	(byte)0x01, (byte)0x11, (byte)0x11, (byte)0x01, (byte)0x10, (byte)0x11, (byte)0x11, (byte)0x10,
	(byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00,
	(byte)0x10, (byte)0x44, (byte)0x44, (byte)0x01, (byte)0x10, (byte)0x55, (byte)0x55, (byte)0x01,
	(byte)0x10, (byte)0x44, (byte)0x44, (byte)0x01, (byte)0x10, (byte)0x55, (byte)0x55, (byte)0x01,
	(byte)0x10, (byte)0x04, (byte)0x44, (byte)0x01, (byte)0x10, (byte)0x55, (byte)0x50, (byte)0x01,
	(byte)0x11, (byte)0x00, (byte)0x44, (byte)0x01, (byte)0x10, (byte)0x55, (byte)0x00, (byte)0x11,
	(byte)0x11, (byte)0x10, (byte)0x00, (byte)0x01, (byte)0x10, (byte)0x00, (byte)0x01, (byte)0x11,
	(byte)0x11, (byte)0x11, (byte)0x11, (byte)0x00, (byte)0x00, (byte)0x11, (byte)0x11, (byte)0x11,
};

static byte[] mskData = {
	(byte)0x03, (byte)0xc0, 
	(byte)0x1f, (byte)0xf8,
	(byte)0x3f, (byte)0xfc,
	(byte)0x7f, (byte)0xfe,
	(byte)0x7f, (byte)0xfe,
	(byte)0x7f, (byte)0xfe,
	(byte)0xff, (byte)0xff,
	(byte)0xfe, (byte)0x7f,
	(byte)0xfe, (byte)0x7f,
	(byte)0xff, (byte)0xff,
	(byte)0x7f, (byte)0xfe,
	(byte)0x7f, (byte)0xfe,
	(byte)0x7f, (byte)0xfe,
	(byte)0x3f, (byte)0xfc,
	(byte)0x1f, (byte)0xf8,
	(byte)0x03, (byte)0xc0
};

public static void main (String [] args) {
	Display display = new Display();
	Color white = display.getSystemColor (SWT.COLOR_WHITE);
	Color black = display.getSystemColor (SWT.COLOR_BLACK);
	Color yellow = display.getSystemColor (SWT.COLOR_YELLOW);
	Color red = display.getSystemColor (SWT.COLOR_RED);
	Color green = display.getSystemColor (SWT.COLOR_GREEN);
	Color blue = display.getSystemColor (SWT.COLOR_BLUE);
	
	//Create a source ImageData of depth 4
	PaletteData palette = new PaletteData (new RGB[] {
		black.getRGB(), white.getRGB(), yellow.getRGB(), 
		red.getRGB(), blue.getRGB(), green.getRGB()});
	ImageData sourceData = new ImageData (16, 16, 4, palette, 1, srcData);
	
	//Create a mask ImageData of depth 1 (monochrome)
	palette = new PaletteData (new RGB [] {black.getRGB(), white.getRGB(),});
	ImageData maskData = new ImageData (16, 16, 1, palette, 1, mskData);

	//Set mask
	sourceData.maskData = maskData.data;
	sourceData.maskPad = maskData.scanlinePad;

	//Create cursor
	Cursor cursor = new Cursor(display, sourceData, 10, 10);

	//Remove mask to draw them separately just to show what they look like
	sourceData.maskData = null;
	sourceData.maskPad = -1;

	Shell shell = new Shell(display);
	final Image source = new Image (display,sourceData);
	final Image mask = new Image (display, maskData);
	shell.addPaintListener(new PaintListener() {
		public void paintControl(PaintEvent e) {
			GC gc = e.gc;
			int x = 10, y = 10;
			String stringSource = "source: ";
			String stringMask = "mask: ";
			gc.drawString(stringSource, x, y);
			gc.drawString(stringMask, x, y + 30);
			x += Math.max(gc.stringExtent(stringSource).x, gc.stringExtent(stringMask).x);
			gc.drawImage(source, x, y);
			gc.drawImage(mask, x, y + 30);
		}
	});
	shell.setSize(150, 150);
	shell.open();
	shell.setCursor(cursor);
	
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	cursor.dispose();
	source.dispose();
	mask.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10023, 1, '[SWT]SnippetSnippet12.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Text example snippet: set the selection (start, end)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet12 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Text text = new Text (shell, SWT.BORDER | SWT.V_SCROLL);
	text.setBounds (10, 10, 100, 100);
	for (int i=0; i<16; i++) {
		text.append ("Line " + i + "\n");
	}
	shell.open ();
	text.setSelection (30, 38);
	System.out.println ("selection=" + text.getSelection ());
	System.out.println ("caret position=" + text.getCaretPosition ());
	System.out.println ("caret location=" + text.getCaretLocation ());
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
} 
}
', now(), now());
insert into SNIPPET values (10024, 1, '[SWT]SnippetSnippet120.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Monitor example snippet: center a shell on the primary monitor
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet120 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setSize (200, 200);
	Monitor primary = display.getPrimaryMonitor ();
	Rectangle bounds = primary.getBounds ();
	Rectangle rect = shell.getBounds ();
	int x = bounds.x + (bounds.width - rect.width) / 2;
	int y = bounds.y + (bounds.height - rect.height) / 2;
	shell.setLocation (x, y);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10025, 1, '[SWT]SnippetSnippet121.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Text example snippet: prompt for a password (set the echo character)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet121 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	Text text = new Text (shell, SWT.SINGLE | SWT.BORDER);
	text.setEchoChar (*);
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10026, 1, '[SWT]SnippetSnippet122.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Clipboard example snippet: enable/disable menu depending on clipboard content availability
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet122 {
	
public static void main(String[] args) {
	Display display = new Display();
	final Clipboard cb = new Clipboard(display);
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	final Text text = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.WRAP);
	Menu menu = new Menu(shell, SWT.POP_UP);
	final MenuItem copyItem = new MenuItem(menu, SWT.PUSH);
	copyItem.setText("Copy");
	copyItem.addSelectionListener(new SelectionAdapter(){
		public void widgetSelected(SelectionEvent e) {
			String selection = text.getSelectionText();
			if (selection.length() == 0) return;
			Object[] data = new Object[]{selection};
			Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
			cb.setContents(data, types);
		}
	});
	final MenuItem pasteItem = new MenuItem(menu, SWT.PUSH);
	pasteItem.setText ("Paste");
	pasteItem.addSelectionListener(new SelectionAdapter(){
		public void widgetSelected(SelectionEvent e) {
			String string = (String)(cb.getContents(TextTransfer.getInstance()));
			if (string != null) text.insert(string);
		}
	});
	menu.addMenuListener(new MenuAdapter() {
		public void menuShown(MenuEvent e) {
			// is copy valid?
			String selection = text.getSelectionText();
			copyItem.setEnabled(selection.length() > 0);
			// is paste valid?
			TransferData[] available = cb.getAvailableTypes();
			boolean enabled = false;
			for (int i = 0; i < available.length; i++) {
				if (TextTransfer.getInstance().isSupportedType(available[i])) {
					enabled = true;
					break;
				}
			}
			pasteItem.setEnabled(enabled);
		}
	});
	text.setMenu (menu);

	shell.setSize(200, 200);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	cb.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10027, 1, '[SWT]SnippetSnippet123.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * OLE and ActiveX example snippet: get events from IE control (win32 only)
 * 
 * This snippet only runs as-is on 32-bit architectures because it uses
 * java integers to represent native pointers.  "long" comments are included
 * throughout the snippet to show where int should be changed to long in
 * order to run on a 64-bit architecture.
 * 
 * NOTE: This snippet uses internal SWT packages that are
 * subject to change without notice.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.internal.ole.win32.*;
import org.eclipse.swt.internal.win32.OS;

public class Snippet123 {

public static void main(String[] args) {
	final Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	OleControlSite controlSite;
	try {
		OleFrame frame = new OleFrame(shell, SWT.NONE);
		controlSite = new OleControlSite(frame, SWT.NONE, "Shell.Explorer");
		controlSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
	} catch (SWTError e) {
		System.out.println("Unable to open activeX control");
		return;
	}
	shell.open();
	
	// IWebBrowser
	final OleAutomation webBrowser = new OleAutomation(controlSite);

	// When a new document is loaded, access the document object for the new page.
	int DownloadComplete = 104;
	controlSite.addEventListener(DownloadComplete, new OleListener() {
		public void handleEvent(OleEvent event) {
			int[] htmlDocumentID = webBrowser.getIDsOfNames(new String[]{"Document"}); 
			if (htmlDocumentID == null) return;
			Variant pVarResult = webBrowser.getProperty(htmlDocumentID[0]);
			if (pVarResult == null || pVarResult.getType() == 0) return;
			//IHTMLDocument2
			OleAutomation htmlDocument = pVarResult.getAutomation();

			// Request to be notified of click, double click and key down events
			EventDispatch myDispatch = new EventDispatch(EventDispatch.onclick);
			IDispatch idispatch = new IDispatch(myDispatch.getAddress());
			Variant dispatch = new Variant(idispatch);
			htmlDocument.setProperty(EventDispatch.onclick, dispatch);

			myDispatch = new EventDispatch(EventDispatch.ondblclick);
			idispatch = new IDispatch(myDispatch.getAddress());
			dispatch = new Variant(idispatch);
			htmlDocument.setProperty(EventDispatch.ondblclick, dispatch);

			myDispatch = new EventDispatch(EventDispatch.onkeydown);
			idispatch = new IDispatch(myDispatch.getAddress());
			dispatch = new Variant(idispatch);
			htmlDocument.setProperty(EventDispatch.onkeydown, dispatch);
			
			//Remember to release OleAutomation Object
			htmlDocument.dispose();
		}
	});
	
	// Navigate to a web site
	int[] ids = webBrowser.getIDsOfNames(new String[]{"Navigate", "URL"}); 
	Variant[] rgvarg = new Variant[] {new Variant("http://www.google.com")};
	int[] rgdispidNamedArgs = new int[]{ids[1]};
	webBrowser.invoke(ids[0], rgvarg, rgdispidNamedArgs);
		
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	//Remember to release OleAutomation Object
	webBrowser.dispose();
	display.dispose();
	
}
}
// EventDispatch implements a simple IDispatch interface which will be called 
// when the event is fired.
class EventDispatch {
	private COMObject iDispatch;
	private int refCount = 0;
	private int eventID;
	
	final static int onhelp = 0x8001177d;
	final static int onclick = 0x80011778;
	final static int ondblclick = 0x80011779;
	final static int onkeyup = 0x80011776;
	final static int onkeydown = 0x80011775;
	final static int onkeypress = 0x80011777;
	final static int onmouseup = 0x80011773;
	final static int onmousedown = 0x80011772;
	final static int onmousemove = 0x80011774;
	final static int onmouseout = 0x80011771;
	final static int onmouseover = 0x80011770;
	final static int onreadystatechange = 0x80011789;
	final static int onafterupdate = 0x80011786;
	final static int onrowexit= 0x80011782;
	final static int onrowenter = 0x80011783;
	final static int ondragstart = 0x80011793;
	final static int onselectstart = 0x80011795;

	EventDispatch(int eventID) {
		this.eventID = eventID;
		createCOMInterfaces();
	}
	int /*long*/ getAddress() {
		return iDispatch.getAddress();
	}
	private void createCOMInterfaces() {
		iDispatch = new COMObject(new int[]{2, 0, 0, 1, 3, 4, 8}){
			public int /*long*/ method0(int /*long*/[] args) {return QueryInterface(args[0], args[1]);}
			public int /*long*/ method1(int /*long*/[] args) {return AddRef();}
			public int /*long*/ method2(int /*long*/[] args) {return Release();}
			// method3 GetTypeInfoCount - not implemented
			// method4 GetTypeInfo - not implemented
			// method5 GetIDsOfNames - not implemented
			public int /*long*/ method6(int /*long*/[] args) {return Invoke((int)/*64*/args[0], args[1], (int)/*64*/args[2], (int)/*64*/args[3], args[4], args[5], args[6], args[7]);}
		};
	}
	private void disposeCOMInterfaces() {
		if (iDispatch != null)
			iDispatch.dispose();
		iDispatch = null;
		
	}
	private int AddRef() {
		refCount++;
		return refCount;
	}
	private int Invoke(int dispIdMember, int /*long*/ riid, int lcid, int dwFlags, int /*long*/ pDispParams, int /*long*/ pVarResult, int /*long*/ pExcepInfo, int /*long*/ pArgErr)	{
		switch (eventID) {
			case onhelp: System.out.println("onhelp"); break;
			case onclick: System.out.println("onclick"); break;
			case ondblclick: System.out.println("ondblclick"); break;
			case onkeyup: System.out.println("onkeyup"); break;
			case onkeydown: System.out.println("onkeydown"); break;
			case onkeypress: System.out.println("onkeypress"); break;
			case onmouseup: System.out.println("onmouseup"); break;
			case onmousedown: System.out.println("onmousedown"); break;
			case onmousemove: System.out.println("onmousemove"); break;
			case onmouseout: System.out.println("onmouseout"); break;
			case onmouseover: System.out.println("onmouseover"); break;
			case onreadystatechange: System.out.println("onreadystatechange"); break;
			case onafterupdate: System.out.println("onafterupdate"); break;
			case onrowexit: System.out.println("onrowexit"); break;
			case onrowenter: System.out.println("onrowenter"); break;
			case ondragstart: System.out.println("ondragstart"); break;
			case onselectstart: System.out.println("onselectstart"); break;
		}
		return COM.S_OK;
	}
	private int QueryInterface(int /*long*/ riid, int /*long*/ ppvObject) {
		if (riid == 0 || ppvObject == 0) return COM.E_INVALIDARG;
		GUID guid = new GUID();
		COM.MoveMemory(guid, riid, GUID.sizeof);
	
		if (COM.IsEqualGUID(guid, COM.IIDIUnknown) || COM.IsEqualGUID(guid, COM.IIDIDispatch)) {
			COM.MoveMemory(ppvObject, new int /*long*/[] {iDispatch.getAddress()}, OS.PTR_SIZEOF);
			AddRef();
			return COM.S_OK;
		}
		COM.MoveMemory(ppvObject, new int /*long*/[] {0}, OS.PTR_SIZEOF);
		return COM.E_NOINTERFACE;
	}
	int Release() {
		refCount--;
		if (refCount == 0) {
			disposeCOMInterfaces();
		}
		return refCount;
	}
}

', now(), now());
insert into SNIPPET values (10028, 1, '[SWT]SnippetSnippet124.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * TableEditor example snippet: edit a cell in a table (in place, fancy)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.custom.*;

public class Snippet124 {
public static void main (String[] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	final Table table = new Table(shell, SWT.BORDER | SWT.MULTI);
	table.setLinesVisible (true);
	for (int i=0; i<3; i++) {
		TableColumn column = new TableColumn (table, SWT.NONE);
		column.setWidth(100);
	}
	for (int i=0; i<3; i++) {
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText(new String [] {"" + i, "" + i , "" + i});
	}
	final TableEditor editor = new TableEditor (table);
	editor.horizontalAlignment = SWT.LEFT;
	editor.grabHorizontal = true;
	table.addListener (SWT.MouseDown, new Listener () {
		public void handleEvent (Event event) {
			Rectangle clientArea = table.getClientArea ();
			Point pt = new Point (event.x, event.y);
			int index = table.getTopIndex ();
			while (index < table.getItemCount ()) {
				boolean visible = false;
				final TableItem item = table.getItem (index);
				for (int i=0; i<table.getColumnCount (); i++) {
					Rectangle rect = item.getBounds (i);
					if (rect.contains (pt)) {
						final int column = i;
						final Text text = new Text (table, SWT.NONE);
						Listener textListener = new Listener () {
							public void handleEvent (final Event e) {
								switch (e.type) {
									case SWT.FocusOut:
										item.setText (column, text.getText ());
										text.dispose ();
										break;
									case SWT.Traverse:
										switch (e.detail) {
											case SWT.TRAVERSE_RETURN:
												item.setText (column, text.getText ());
												//FALL THROUGH
											case SWT.TRAVERSE_ESCAPE:
												text.dispose ();
												e.doit = false;
										}
										break;
								}
							}
						};
						text.addListener (SWT.FocusOut, textListener);
						text.addListener (SWT.Traverse, textListener);
						editor.setEditor (text, item, i);
						text.setText (item.getText (i));
						text.selectAll ();
						text.setFocus ();
						return;
					}
					if (!visible && rect.intersects (clientArea)) {
						visible = true;
					}
				}
				if (!visible) return;
				index++;
			}
		}
	});
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10029, 1, '[SWT]SnippetSnippet125.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tool Tips example snippet: create fake tool tips for items in a table
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet125 {

public static void main (String[] args) {
	final Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	final Table table = new Table (shell, SWT.BORDER);
	for (int i = 0; i < 20; i++) {
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText ("item " + i);
	}
	// Disable native tooltip
	table.setToolTipText ("");
	
	// Implement a "fake" tooltip
	final Listener labelListener = new Listener () {
		public void handleEvent (Event event) {
			Label label = (Label)event.widget;
			Shell shell = label.getShell ();
			switch (event.type) {
				case SWT.MouseDown:
					Event e = new Event ();
					e.item = (TableItem) label.getData ("_TABLEITEM");
					// Assuming table is single select, set the selection as if
					// the mouse down event went through to the table
					table.setSelection (new TableItem [] {(TableItem) e.item});
					table.notifyListeners (SWT.Selection, e);
					shell.dispose ();
					table.setFocus();
					break;
				case SWT.MouseExit:
					shell.dispose ();
					break;
			}
		}
	};
	
	Listener tableListener = new Listener () {
		Shell tip = null;
		Label label = null;
		public void handleEvent (Event event) {
			switch (event.type) {
				case SWT.Dispose:
				case SWT.KeyDown:
				case SWT.MouseMove: {
					if (tip == null) break;
					tip.dispose ();
					tip = null;
					label = null;
					break;
				}
				case SWT.MouseHover: {
					TableItem item = table.getItem (new Point (event.x, event.y));
					if (item != null) {
						if (tip != null  && !tip.isDisposed ()) tip.dispose ();
						tip = new Shell (shell, SWT.ON_TOP | SWT.NO_FOCUS | SWT.TOOL);
						tip.setBackground (display.getSystemColor (SWT.COLOR_INFO_BACKGROUND));
						FillLayout layout = new FillLayout ();
						layout.marginWidth = 2;
						tip.setLayout (layout);
						label = new Label (tip, SWT.NONE);
						label.setForeground (display.getSystemColor (SWT.COLOR_INFO_FOREGROUND));
						label.setBackground (display.getSystemColor (SWT.COLOR_INFO_BACKGROUND));
						label.setData ("_TABLEITEM", item);
						label.setText (item.getText ());
						label.addListener (SWT.MouseExit, labelListener);
						label.addListener (SWT.MouseDown, labelListener);
						Point size = tip.computeSize (SWT.DEFAULT, SWT.DEFAULT);
						Rectangle rect = item.getBounds (0);
						Point pt = table.toDisplay (rect.x, rect.y);
						tip.setBounds (pt.x, pt.y, size.x, size.y);
						tip.setVisible (true);
					}
				}
			}
		}
	};
	table.addListener (SWT.Dispose, tableListener);
	table.addListener (SWT.KeyDown, tableListener);
	table.addListener (SWT.MouseMove, tableListener);
	table.addListener (SWT.MouseHover, tableListener);
	shell.pack ();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10030, 1, '[SWT]SnippetSnippet126.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Table example snippet: place arbitrary controls in a table
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.custom.*;

public class Snippet126 {
public static void main(String[] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	Table table = new Table (shell, SWT.BORDER | SWT.MULTI);
	table.setLinesVisible (true);
	for (int i=0; i<3; i++) {
		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setWidth (100);
	}
	for (int i=0; i<12; i++) {
		new TableItem (table, SWT.NONE);
	}
	TableItem [] items = table.getItems ();
	for (int i=0; i<items.length; i++) {
		TableEditor editor = new TableEditor (table);
		CCombo combo = new CCombo (table, SWT.NONE);
		combo.setText("CCombo");
		combo.add("item 1");
		combo.add("item 2");
		editor.grabHorizontal = true;
		editor.setEditor(combo, items[i], 0);
		editor = new TableEditor (table);
		Text text = new Text (table, SWT.NONE);
		text.setText("Text");
		editor.grabHorizontal = true;
		editor.setEditor(text, items[i], 1);
		editor = new TableEditor (table);
		Button button = new Button (table, SWT.CHECK);
		button.pack ();
		editor.minimumWidth = button.getSize ().x;
		editor.horizontalAlignment = SWT.LEFT;
		editor.setEditor (button, items[i], 2);
	}
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10031, 1, '[SWT]SnippetSnippet127.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Control example snippet: prevent Tab from traversing out of a control
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet127 {
public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new RowLayout ());
	Button button1 = new Button(shell, SWT.PUSH);
	button1.setText("Cant Traverse");
	button1.addTraverseListener(new TraverseListener () {
		public void keyTraversed(TraverseEvent e) {
			switch (e.detail) {
				case SWT.TRAVERSE_TAB_NEXT:
				case SWT.TRAVERSE_TAB_PREVIOUS: {
					e.doit = false;
				}
			}
		}
	});
	Button button2 = new Button (shell, SWT.PUSH);
	button2.setText("Can Traverse");
	shell.pack ();
	shell.open();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10032, 1, '[SWT]SnippetSnippet128.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Browser example snippet: bring up a browser
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.browser.*;

public class Snippet128 {
	public static void main(String [] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 3;
		shell.setLayout(gridLayout);
		ToolBar toolbar = new ToolBar(shell, SWT.NONE);
		ToolItem itemBack = new ToolItem(toolbar, SWT.PUSH);
		itemBack.setText("Back");
		ToolItem itemForward = new ToolItem(toolbar, SWT.PUSH);
		itemForward.setText("Forward");
		ToolItem itemStop = new ToolItem(toolbar, SWT.PUSH);
		itemStop.setText("Stop");
		ToolItem itemRefresh = new ToolItem(toolbar, SWT.PUSH);
		itemRefresh.setText("Refresh");
		ToolItem itemGo = new ToolItem(toolbar, SWT.PUSH);
		itemGo.setText("Go");
		
		GridData data = new GridData();
		data.horizontalSpan = 3;
		toolbar.setLayoutData(data);

		Label labelAddress = new Label(shell, SWT.NONE);
		labelAddress.setText("Address");
		
		final Text location = new Text(shell, SWT.BORDER);
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.horizontalSpan = 2;
		data.grabExcessHorizontalSpace = true;
		location.setLayoutData(data);

		final Browser browser;
		try {
			browser = new Browser(shell, SWT.NONE);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			return;
		}
		data = new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.FILL;
		data.horizontalSpan = 3;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		browser.setLayoutData(data);

		final Label status = new Label(shell, SWT.NONE);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		status.setLayoutData(data);

		final ProgressBar progressBar = new ProgressBar(shell, SWT.NONE);
		data = new GridData();
		data.horizontalAlignment = GridData.END;
		progressBar.setLayoutData(data);

		/* event handling */
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				ToolItem item = (ToolItem)event.widget;
				String string = item.getText();
				if (string.equals("Back")) browser.back(); 
				else if (string.equals("Forward")) browser.forward();
				else if (string.equals("Stop")) browser.stop();
				else if (string.equals("Refresh")) browser.refresh();
				else if (string.equals("Go")) browser.setUrl(location.getText());
		   }
		};
		browser.addProgressListener(new ProgressListener() {
			public void changed(ProgressEvent event) {
					if (event.total == 0) return;                            
					int ratio = event.current * 100 / event.total;
					progressBar.setSelection(ratio);
			}
			public void completed(ProgressEvent event) {
				progressBar.setSelection(0);
			}
		});
		browser.addStatusTextListener(new StatusTextListener() {
			public void changed(StatusTextEvent event) {
				status.setText(event.text);	
			}
		});
		browser.addLocationListener(new LocationListener() {
			public void changed(LocationEvent event) {
				if (event.top) location.setText(event.location);
			}
			public void changing(LocationEvent event) {
			}
		});
		itemBack.addListener(SWT.Selection, listener);
		itemForward.addListener(SWT.Selection, listener);
		itemStop.addListener(SWT.Selection, listener);
		itemRefresh.addListener(SWT.Selection, listener);
		itemGo.addListener(SWT.Selection, listener);
		location.addListener(SWT.DefaultSelection, new Listener() {
			public void handleEvent(Event e) {
				browser.setUrl(location.getText());
			}
		});
		
		shell.open();
		browser.setUrl("http://eclipse.org");
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10033, 1, '[SWT]SnippetSnippet129.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Table example snippet: color cells and rows in table
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet129 {
 
public static void main(String[] args) {
	Display display = new Display();
	Color red = display.getSystemColor(SWT.COLOR_RED);
	Color blue = display.getSystemColor(SWT.COLOR_BLUE);
	Color white = display.getSystemColor(SWT.COLOR_WHITE);
	Color gray = display.getSystemColor(SWT.COLOR_GRAY);
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	Table table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
	table.setBackground(gray);
	TableColumn column1 = new TableColumn(table, SWT.NONE);
	TableColumn column2 = new TableColumn(table, SWT.NONE);
	TableColumn column3 = new TableColumn(table, SWT.NONE);
	TableItem item = new TableItem(table, SWT.NONE);
	item.setText(new String[] {"entire","row","red foreground"});
	item.setForeground(red);
	item = new TableItem(table, SWT.NONE);
	item.setText(new String[] {"entire","row","red background"});
	item.setBackground(red);
	item = new TableItem(table, SWT.NONE);
	item.setText(new String[] {"entire","row","white fore/red back"});
	item.setForeground(white);
	item.setBackground(red);
	item = new TableItem(table, SWT.NONE);
	item.setText(new String[] {"normal","blue foreground","red foreground"});
	item.setForeground(1, blue);
	item.setForeground(2, red);
	item = new TableItem(table, SWT.NONE);
	item.setText(new String[] {"normal","blue background","red background"});
	item.setBackground(1, blue);
	item.setBackground(2, red);
	item = new TableItem(table, SWT.NONE);
	item.setText(new String[] {"white fore/blue back","normal","white fore/red back"});
	item.setForeground(0, white);
	item.setBackground(0, blue);
	item.setForeground(2, white);
	item.setBackground(2, red);
	
	column1.pack();
	column2.pack();
	column3.pack();
	
	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10034, 1, '[SWT]SnippetSnippet13.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * GC example snippet: draw a thick line
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet13 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.open ();
	GC gc = new GC (shell);
	gc.setLineWidth (4);
	gc.drawRectangle (20, 20, 100, 100);
	gc.dispose ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10035, 1, '[SWT]SnippetSnippet130.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * BusyIndicator example snippet: display busy cursor during long running task
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet130 {

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		final Text text = new Text(shell, SWT.MULTI | SWT.BORDER | SWT.V_SCROLL);
		text.setLayoutData(new GridData(GridData.FILL_BOTH));
		final int[] nextId = new int[1];
		Button b = new Button(shell, SWT.PUSH);
		b.setText("invoke long running job");
		b.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				Runnable longJob = new Runnable() {
					boolean done = false;
					int id;
					public void run() {
						Thread thread = new Thread(new Runnable() {
							public void run() {
								id = nextId[0]++;
								display.syncExec(new Runnable() {
									public void run() {
										if (text.isDisposed()) return;
										text.append("\nStart long running task "+id);
									}
								});
								for (int i = 0; i < 100000; i++) {
									if (display.isDisposed()) return;
									System.out.println("do task that takes a long time in a separate thread "+id);
								}
								if (display.isDisposed()) return;
								display.syncExec(new Runnable() {
									public void run() {
										if (text.isDisposed()) return;
										text.append("\nCompleted long running task "+id);
									}
								});
								done = true;
								display.wake();
							}
						});
						thread.start();
						while (!done && !shell.isDisposed()) {
							if (!display.readAndDispatch())
								display.sleep();
						}
					}
				};
				BusyIndicator.showWhile(display, longJob);
			}
		});
		shell.setSize(250, 150);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10036, 1, '[SWT]SnippetSnippet131.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Menu example snippet: show a popup menu (wait for it to close)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet131 {
public static void main (String [] args) {
	final Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.addListener (SWT.MenuDetect, new Listener () {
		public void handleEvent (Event event) {
			Menu menu = new Menu (shell, SWT.POP_UP);
			MenuItem item = new MenuItem (menu, SWT.PUSH);
			item.setText ("Menu Item");
			item.addListener (SWT.Selection, new Listener () {
				public void handleEvent (Event e) {
					System.out.println ("Item Selected");
				}
			});
			menu.setLocation (event.x, event.y);
			menu.setVisible (true);
			while (!menu.isDisposed () && menu.isVisible ()) {
				if (!display.readAndDispatch ()) display.sleep ();
			}
			menu.dispose ();
		}
	});
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10037, 1, '[SWT]SnippetSnippet132.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
  
/*
 * Printing example snippet: print "Hello World!" in black, outlined in red, to default printer
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.printing.*;

public class Snippet132 {

public static void main (String [] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.open ();
	PrinterData data = Printer.getDefaultPrinterData();
	if (data == null) {
		System.out.println("Warning: No default printer.");
		return;
	}
	Printer printer = new Printer(data);
	if (printer.startJob("SWT Printing Snippet")) {
		Color black = printer.getSystemColor(SWT.COLOR_BLACK);
		Color white = printer.getSystemColor(SWT.COLOR_WHITE);
		Color red = printer.getSystemColor(SWT.COLOR_RED);
		Rectangle trim = printer.computeTrim(0, 0, 0, 0);
		Point dpi = printer.getDPI();
		int leftMargin = dpi.x + trim.x; // one inch from left side of paper
		int topMargin = dpi.y / 2 + trim.y; // one-half inch from top edge of paper
		GC gc = new GC(printer);
		if (printer.startPage()) {
			gc.setBackground(white);
			gc.setForeground(black);
			String testString = "Hello World!";
			Point extent = gc.stringExtent(testString);
			gc.drawString(testString, leftMargin, topMargin);
			gc.setForeground(red);
			gc.drawRectangle(leftMargin, topMargin, extent.x, extent.y);
			printer.endPage();
		}
		gc.dispose();
		printer.endJob();
	}
	printer.dispose();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10038, 1, '[SWT]SnippetSnippet133.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
  
/*
 * Printing example snippet: print text to printer, with word wrap and pagination
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import java.io.*;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.printing.*;

public class Snippet133 {
	Display display;
	Shell shell;
	Text text;
	Font font;
	Color foregroundColor, backgroundColor;
	
	Printer printer;
	GC gc;
	FontData[] printerFontData;
	RGB printerForeground, printerBackground;

	int lineHeight = 0;
	int tabWidth = 0;
	int leftMargin, rightMargin, topMargin, bottomMargin;
	int x, y;
	int index, end;
	String textToPrint;
	String tabs;
	StringBuffer wordBuffer;

	public static void main(String[] args) {
		new Snippet133().open();
	}
	
	void open() {
		display = new Display();
		shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText("Print Text");
		text = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		MenuItem item = new MenuItem(menuBar, SWT.CASCADE);
		item.setText("&File");
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		item.setMenu(fileMenu);
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("&Open...");
		item.setAccelerator(SWT.CTRL + O);
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuOpen();
			}
		});
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("Font...");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuFont();
			}
		});
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("Foreground Color...");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuForegroundColor();
			}
		});
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("Background Color...");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuBackgroundColor();
			}
		});
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("&Print...");
		item.setAccelerator(SWT.CTRL + P);
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				menuPrint();
			}
		});
		new MenuItem(fileMenu, SWT.SEPARATOR);
		item = new MenuItem(fileMenu, SWT.PUSH);
		item.setText("E&xit");
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				System.exit(0);
			}
		});

		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		if (font != null) font.dispose();
		if (foregroundColor != null) foregroundColor.dispose();
		if (backgroundColor != null) backgroundColor.dispose();
		display.dispose();
	}
		
	void menuOpen() {
		final String textString;
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setFilterExtensions(new String[] {"*.java", "*.*"});
		String name = dialog.open();
		if ((name == null) || (name.length() == 0)) return;
	
		try {
			File file = new File(name);
			FileInputStream stream= new FileInputStream(file.getPath());
			try {
				Reader in = new BufferedReader(new InputStreamReader(stream));
				char[] readBuffer= new char[2048];
				StringBuffer buffer= new StringBuffer((int) file.length());
				int n;
				while ((n = in.read(readBuffer)) > 0) {
					buffer.append(readBuffer, 0, n);
				}
				textString = buffer.toString();
				stream.close();
			} catch (IOException e) {
				MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
				box.setMessage("Error reading file:\n" + name);
				box.open();
				return;
			}
		} catch (FileNotFoundException e) {
			MessageBox box = new MessageBox(shell, SWT.ICON_ERROR);
			box.setMessage("File not found:\n" + name);
			box.open();
			return;
		}	
		text.setText(textString);
	}

	void menuFont() {
		FontDialog fontDialog = new FontDialog(shell);
		fontDialog.setFontList(text.getFont().getFontData());
		FontData fontData = fontDialog.open();
		if (fontData != null) {
			if (font != null) font.dispose();
			font = new Font(display, fontData);
			text.setFont(font);
		}
	}

	void menuForegroundColor() {
		ColorDialog colorDialog = new ColorDialog(shell);
		colorDialog.setRGB(text.getForeground().getRGB());
		RGB rgb = colorDialog.open();
		if (rgb != null) {
			if (foregroundColor != null) foregroundColor.dispose();
			foregroundColor = new Color(display, rgb);
			text.setForeground(foregroundColor);
		}
	}

	void menuBackgroundColor() {
		ColorDialog colorDialog = new ColorDialog(shell);
		colorDialog.setRGB(text.getBackground().getRGB());
		RGB rgb = colorDialog.open();
		if (rgb != null) {
			if (backgroundColor != null) backgroundColor.dispose();
			backgroundColor = new Color(display, rgb);
			text.setBackground(backgroundColor);
		}
	}

	void menuPrint() {
		PrintDialog dialog = new PrintDialog(shell, SWT.NONE);
		PrinterData data = dialog.open();
		if (data == null) return;
		if (data.printToFile) {
			data.fileName = "print.out"; // you probably want to ask the user for a filename
		}
		
		/* Get the text to print from the Text widget (you could get it from anywhere, i.e. your java model) */
		textToPrint = text.getText();

		/* Get the font & foreground & background data. */
		printerFontData = text.getFont().getFontData();
		printerForeground = text.getForeground().getRGB();
		printerBackground = text.getBackground().getRGB();
		
		/* Do the printing in a background thread so that spooling does not freeze the UI. */
		printer = new Printer(data);
		Thread printingThread = new Thread("Printing") {
			public void run() {
				print(printer);
				printer.dispose();
			}
		};
		printingThread.start();
	}
	
	void print(Printer printer) {
		if (printer.startJob("Text")) {   // the string is the job name - shows up in the printers job list
			Rectangle clientArea = printer.getClientArea();
			Rectangle trim = printer.computeTrim(0, 0, 0, 0);
			Point dpi = printer.getDPI();
			leftMargin = dpi.x + trim.x; // one inch from left side of paper
			rightMargin = clientArea.width - dpi.x + trim.x + trim.width; // one inch from right side of paper
			topMargin = dpi.y + trim.y; // one inch from top edge of paper
			bottomMargin = clientArea.height - dpi.y + trim.y + trim.height; // one inch from bottom edge of paper
			
			/* Create a buffer for computing tab width. */
			int tabSize = 4; // is tab width a user setting in your UI?
			StringBuffer tabBuffer = new StringBuffer(tabSize);
			for (int i = 0; i < tabSize; i++) tabBuffer.append( );
			tabs = tabBuffer.toString();

			/* Create printer GC, and create and set the printer font & foreground color. */
			gc = new GC(printer);
			Font printerFont = new Font(printer, printerFontData);
			Color printerForegroundColor = new Color(printer, printerForeground);
			Color printerBackgroundColor = new Color(printer, printerBackground); 
			
			gc.setFont(printerFont);
			gc.setForeground(printerForegroundColor);
			gc.setBackground(printerBackgroundColor);
			tabWidth = gc.stringExtent(tabs).x;
			lineHeight = gc.getFontMetrics().getHeight();
		
			/* Print text to current gc using word wrap */
			printText();
			printer.endJob();

			/* Cleanup graphics resources used in printing */
			printerFont.dispose();
			printerForegroundColor.dispose();
			printerBackgroundColor.dispose();
			gc.dispose();
		}
	}
	
	void printText() {
		printer.startPage();
		wordBuffer = new StringBuffer();
		x = leftMargin;
		y = topMargin;
		index = 0;
		end = textToPrint.length();
		while (index < end) {
			char c = textToPrint.charAt(index);
			index++;
			if (c != 0) {
				if (c == 0x0a || c == 0x0d) {
					if (c == 0x0d && index < end && textToPrint.charAt(index) == 0x0a) {
						index++; // if this is cr-lf, skip the lf
					}
					printWordBuffer();
					newline();
				} else {
					if (c != \t) {
						wordBuffer.append(c);
					}
					if (Character.isWhitespace(c)) {
						printWordBuffer();
						if (c == \t) {
							x += tabWidth;
						}
					}
				}
			}
		}
		if (y + lineHeight <= bottomMargin) {
			printer.endPage();
		}
	}

	void printWordBuffer() {
		if (wordBuffer.length() > 0) {
			String word = wordBuffer.toString();
			int wordWidth = gc.stringExtent(word).x;
			if (x + wordWidth > rightMargin) {
				/* word doesnt fit on current line, so wrap */
				newline();
			}
			gc.drawString(word, x, y, false);
			x += wordWidth;
			wordBuffer = new StringBuffer();
		}
	}

	void newline() {
		x = leftMargin;
		y += lineHeight;
		if (y + lineHeight > bottomMargin) {
			printer.endPage();
			if (index + 1 < end) {
				y = topMargin;
				printer.startPage();
			}
		}
	}
}
', now(), now());
insert into SNIPPET values (10039, 1, '[SWT]SnippetSnippet134.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Shell example snippet: create a non-rectangular window
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet134 {

static int[] circle(int r, int offsetX, int offsetY) {
	int[] polygon = new int[8 * r + 4];
	//x^2 + y^2 = r^2
	for (int i = 0; i < 2 * r + 1; i++) {
		int x = i - r;
		int y = (int)Math.sqrt(r*r - x*x);
		polygon[2*i] = offsetX + x;
		polygon[2*i+1] = offsetY + y;
		polygon[8*r - 2*i - 2] = offsetX + x;
		polygon[8*r - 2*i - 1] = offsetY - y;
	}
	return polygon;
}

public static void main(String[] args) {
	final Display display = new Display();
	//Shell must be created with style SWT.NO_TRIM
	final Shell shell = new Shell(display, SWT.NO_TRIM | SWT.ON_TOP);
	shell.setBackground(display.getSystemColor(SWT.COLOR_RED));
	//define a region that looks like a key hole
	Region region = new Region();
	region.add(circle(67, 67, 67));
	region.subtract(circle(20, 67, 50));
	region.subtract(new int[]{67, 50, 55, 105, 79, 105});
	//define the shape of the shell using setRegion
	shell.setRegion(region);
	Rectangle size = region.getBounds();
	shell.setSize(size.width, size.height);
	//add ability to move shell around
	Listener l = new Listener() {
		Point origin;
		public void handleEvent(Event e) {
			switch (e.type) {
				case SWT.MouseDown:
					origin = new Point(e.x, e.y);
					break;
				case SWT.MouseUp:
					origin = null;
					break;
				case SWT.MouseMove:
					if (origin != null) {
						Point p = display.map(shell, null, e.x, e.y);
						shell.setLocation(p.x - origin.x, p.y - origin.y);
					}
					break;
			}
		}
	};
	shell.addListener(SWT.MouseDown, l);
	shell.addListener(SWT.MouseUp, l);
	shell.addListener(SWT.MouseMove, l);
	//add ability to close shell
	Button b = new Button(shell, SWT.PUSH);
	b.setBackground(shell.getBackground());
	b.setText("close");
	b.pack();
	b.setLocation(10, 68);
	b.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event e) {
			shell.close();
		}
	});
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	region.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10040, 1, '[SWT]SnippetSnippet135.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * example snippet: embed Swing/AWT in SWT
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import java.awt.EventQueue;
import java.io.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.awt.SWT_AWT;

public class Snippet135 {

	static class FileTableModel extends AbstractTableModel {		
		File[] files;        
		String[] columnsName = {"Name", "Size", "Date Modified"};
		
		public FileTableModel (File[] files) {
			this.files = files;
		}
		public int getColumnCount () {
			return columnsName.length;
		}
		public Class getColumnClass (int col) {
			if (col == 1) return Long.class;
			if (col == 2) return Date.class;
			return String.class;
		}
		public int getRowCount () {
			return files == null ? 0 : files.length;
		}
		public Object getValueAt (int row, int col) {
			if (col == 0) return files[row].getName();
			if (col == 1) return new Long(files[row].length());
			if (col == 2) return new Date(files[row].lastModified());
			return "";
		}
		public String getColumnName (int col) {
			return columnsName[col];
		}
	}

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("SWT and Swing/AWT Example");

		Listener exitListener = new Listener() {
			public void handleEvent(Event e) {
				MessageBox dialog = new MessageBox(shell, SWT.OK | SWT.CANCEL | SWT.ICON_QUESTION);
				dialog.setText("Question");
				dialog.setMessage("Exit?");
				if (e.type == SWT.Close) e.doit = false;
				if (dialog.open() != SWT.OK) return;
				shell.dispose();
			}
		};	
		Listener aboutListener = new Listener() {
			public void handleEvent(Event e) {
				final Shell s = new Shell(shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				s.setText("About");
				GridLayout layout = new GridLayout(1, false);
				layout.verticalSpacing = 20;
				layout.marginHeight = layout.marginWidth = 10;
				s.setLayout(layout);
				Label label = new Label(s, SWT.NONE);
				label.setText("SWT and AWT Example.");
				Button button = new Button(s, SWT.PUSH);
				button.setText("OK");
				GridData data = new GridData();
				data.horizontalAlignment = GridData.CENTER;
				button.setLayoutData(data);
				button.addListener(SWT.Selection, new Listener() {
					public void handleEvent(Event event) {
						s.dispose();
					}
				});
				s.pack();
				Rectangle parentBounds = shell.getBounds();
				Rectangle bounds = s.getBounds();
				int x = parentBounds.x + (parentBounds.width - bounds.width) / 2;
				int y = parentBounds.y + (parentBounds.height - bounds.height) / 2;
				s.setLocation(x, y);
				s.open();
				while (!s.isDisposed()) {
					if (!display.readAndDispatch()) display.sleep();
				}
			}
		};
		shell.addListener(SWT.Close, exitListener);
		Menu mb = new Menu(shell, SWT.BAR);
		MenuItem fileItem = new MenuItem(mb, SWT.CASCADE);
		fileItem.setText("&File");
		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileItem.setMenu(fileMenu);
		MenuItem exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText("&Exit\tCtrl+X");
		exitItem.setAccelerator(SWT.CONTROL + X);
		exitItem.addListener(SWT.Selection, exitListener);
		MenuItem aboutItem = new MenuItem(fileMenu, SWT.PUSH);
		aboutItem.setText("&About\tCtrl+A");
		aboutItem.setAccelerator(SWT.CONTROL + A);
		aboutItem.addListener(SWT.Selection, aboutListener);
		shell.setMenuBar(mb);

		RGB color = shell.getBackground().getRGB();
		Label separator1 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		Label locationLb = new Label(shell, SWT.NONE);
		locationLb.setText("Location:");
		Composite locationComp = new Composite(shell, SWT.EMBEDDED);
		ToolBar toolBar = new ToolBar(shell, SWT.FLAT);
		ToolItem exitToolItem = new ToolItem(toolBar, SWT.PUSH);
		exitToolItem.setText("&Exit");
		exitToolItem.addListener(SWT.Selection, exitListener);
		ToolItem aboutToolItem = new ToolItem(toolBar, SWT.PUSH);
		aboutToolItem.setText("&About");
		aboutToolItem.addListener(SWT.Selection, aboutListener);
		Label separator2 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		final Composite comp = new Composite(shell, SWT.NONE);
		final Tree fileTree = new Tree(comp, SWT.SINGLE | SWT.BORDER);
		Sash sash = new Sash(comp, SWT.VERTICAL);
		Composite tableComp = new Composite(comp, SWT.EMBEDDED);
		Label separator3 = new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL);
		Composite statusComp = new Composite(shell, SWT.EMBEDDED);

		java.awt.Frame locationFrame = SWT_AWT.new_Frame(locationComp);
		final java.awt.TextField locationText = new java.awt.TextField();
		locationFrame.add(locationText);

		java.awt.Frame fileTableFrame = SWT_AWT.new_Frame(tableComp);
		java.awt.Panel panel = new java.awt.Panel(new java.awt.BorderLayout());
		fileTableFrame.add(panel);
		final JTable fileTable = new JTable(new FileTableModel(null));
		fileTable.setDoubleBuffered(true);
		fileTable.setShowGrid(false);
		fileTable.createDefaultColumnsFromModel();
		JScrollPane scrollPane = new JScrollPane(fileTable);
		panel.add(scrollPane);

		java.awt.Frame statusFrame = SWT_AWT.new_Frame(statusComp);
		statusFrame.setBackground(new java.awt.Color(color.red, color.green, color.blue));
		final java.awt.Label statusLabel = new java.awt.Label();
		statusFrame.add(statusLabel);
		statusLabel.setText("Select a file");

		sash.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if (e.detail == SWT.DRAG) return;
				GridData data = (GridData)fileTree.getLayoutData();
				Rectangle trim = fileTree.computeTrim(0, 0, 0, 0);
				data.widthHint = e.x - trim.width;
				comp.layout();
			}
		});

		File[] roots = File.listRoots();
		for (int i = 0; i < roots.length; i++) {
			File file = roots[i];
			TreeItem treeItem = new TreeItem(fileTree, SWT.NONE);
			treeItem.setText(file.getAbsolutePath());
			treeItem.setData(file);
			new TreeItem(treeItem, SWT.NONE);
		}
		fileTree.addListener(SWT.Expand, new Listener() {
			public void handleEvent(Event e) {
				TreeItem item = (TreeItem)e.item;
				if (item == null) return;
				if (item.getItemCount() == 1) {
					TreeItem firstItem = item.getItems()[0];
					if (firstItem.getData() != null) return;
					firstItem.dispose();
				} else {
					return;
				}
				File root = (File)item.getData();
				File[] files = root.listFiles();
				if (files == null) return;
				for (int i = 0; i < files.length; i++) {
					File file = files[i];
					if (file.isDirectory()) {
						TreeItem treeItem = new TreeItem(item, SWT.NONE);
						treeItem.setText(file.getName());
						treeItem.setData(file);
						new TreeItem(treeItem, SWT.NONE);
					}
				}
			}
		});
		fileTree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				TreeItem item = (TreeItem)e.item;
				if (item == null) return;
				final File root = (File)item.getData();
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						statusLabel.setText(root.getAbsolutePath());
						locationText.setText(root.getAbsolutePath());
						fileTable.setModel(new FileTableModel(root.listFiles()));
					}
				});
			}
		});
		
		GridLayout layout = new GridLayout(4, false);
		layout.marginWidth = layout.marginHeight = 0;
		layout.horizontalSpacing = layout.verticalSpacing = 1;
		shell.setLayout(layout);
		GridData data;		
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 4;
		separator1.setLayoutData(data);
		data = new GridData();
		data.horizontalSpan = 1;
		data.horizontalIndent = 10;
		locationLb.setLayoutData(data);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 2;
		data.heightHint = locationText.getPreferredSize().height;
		locationComp.setLayoutData(data);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 1;
		toolBar.setLayoutData(data);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 4;
		separator2.setLayoutData(data);
		data = new GridData(GridData.FILL_BOTH);
		data.horizontalSpan = 4;
		comp.setLayoutData(data);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 4;
		separator3.setLayoutData(data);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalSpan = 4;
		data.heightHint = statusLabel.getPreferredSize().height;
		statusComp.setLayoutData(data);
		
		layout = new GridLayout(3, false);
		layout.marginWidth = layout.marginHeight = 0;
		layout.horizontalSpacing = layout.verticalSpacing = 1;
		comp.setLayout(layout);			
		data = new GridData(GridData.FILL_VERTICAL);
		data.widthHint = 200;
		fileTree.setLayoutData(data);		
		data = new GridData(GridData.FILL_VERTICAL);
		sash.setLayoutData(data);		
		data = new GridData(GridData.FILL_BOTH);
		tableComp.setLayoutData(data);

		shell.open();
		while(!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10041, 1, '[SWT]SnippetSnippet136.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Browser example snippet: render HTML from memory
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet136 {
	public static void main(String [] args) {
		String html = "<HTML><HEAD><TITLE>HTML Test</TITLE></HEAD><BODY>";
		for (int i = 0; i < 100; i++) html += "<P>This is line "+i+"</P>";
		html += "</BODY></HTML>";

		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		Browser browser;
		try {
			browser = new Browser(shell, SWT.NONE);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			return;
		}
		browser.setText(html);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}


', now(), now());
insert into SNIPPET values (10042, 1, '[SWT]SnippetSnippet137.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Browser example snippet: render HTML that includes relative links from memory
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet137 {

public static void main(String [] args) {
	/* Relative links: use the HTML base tag */
	String html = "<html><head>"+
		"<base href=\"http://www.eclipse.org/swt/\" >"+
		"<title>HTML Test</title></head>"+
		"<body><a href=\"faq.php\">local link</a></body></html>";

	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	Browser browser;
	try {
		browser = new Browser(shell, SWT.NONE);
	} catch (SWTError e) {
		System.out.println("Could not instantiate Browser: " + e.getMessage());
		return;
	}
	browser.setText(html);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10043, 1, '[SWT]SnippetSnippet138.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * example snippet: set icons with different resolutions
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet138 {
	public static void main(String[] args) {
		Display display = new Display();
		
		Image small = new Image(display, 16, 16);
		GC gc = new GC(small);
		gc.setBackground(display.getSystemColor(SWT.COLOR_RED));
		gc.fillArc(0, 0, 16, 16, 45, 270);
		gc.dispose();
		
		Image large = new Image(display, 32, 32);
		gc = new GC(large);
		gc.setBackground(display.getSystemColor(SWT.COLOR_RED));
		gc.fillArc(0, 0, 32, 32, 45, 270);
		gc.dispose();
		
		/* Provide different resolutions for icons to get
		 * high quality rendering wherever the OS needs 
		 * large icons. For example, the ALT+TAB window 
		 * on certain systems uses a larger icon.
		 */
		Shell shell = new Shell(display);
		shell.setText("Small and Large icons");
		shell.setImages(new Image[] {small, large});

		/* No large icon: the OS will scale up the
		 * small icon when it needs a large one.
		 */
		Shell shell2 = new Shell(display);
		shell2.setText("Small icon");
		shell2.setImage(small);
		
		shell.open();
		shell2.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		small.dispose();
		large.dispose();
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10044, 1, '[SWT]SnippetSnippet139.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * This snippet was adapted from org.eclipse.draw2d.ImageUtilities in 
 * http://dev.eclipse.org/viewcvs/index.cgi/org.eclipse.draw2d/?cvsroot=Tools_Project
 * by Pratik Shah.
 *
 * example snippet: rotate and flip an image
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet139 {	

static ImageData rotate(ImageData srcData, int direction) {
	int bytesPerPixel = srcData.bytesPerLine / srcData.width;
	int destBytesPerLine = (direction == SWT.DOWN)? srcData.width * bytesPerPixel : srcData.height * bytesPerPixel;
	byte[] newData = new byte[(direction == SWT.DOWN)? srcData.height * destBytesPerLine : srcData.width * destBytesPerLine];
	int width = 0, height = 0;
	for (int srcY = 0; srcY < srcData.height; srcY++) {
		for (int srcX = 0; srcX < srcData.width; srcX++) {
			int destX = 0, destY = 0, destIndex = 0, srcIndex = 0;
			switch (direction){
				case SWT.LEFT: // left 90 degrees
					destX = srcY;
					destY = srcData.width - srcX - 1;
					width = srcData.height;
					height = srcData.width; 
					break;
				case SWT.RIGHT: // right 90 degrees
					destX = srcData.height - srcY - 1;
					destY = srcX;
					width = srcData.height;
					height = srcData.width; 
					break;
				case SWT.DOWN: // 180 degrees
					destX = srcData.width - srcX - 1;
					destY = srcData.height - srcY - 1;
					width = srcData.width;
					height = srcData.height; 
					break;
			}
			destIndex = (destY * destBytesPerLine) + (destX * bytesPerPixel);
			srcIndex = (srcY * srcData.bytesPerLine) + (srcX * bytesPerPixel);
			System.arraycopy(srcData.data, srcIndex, newData, destIndex, bytesPerPixel);
		}
	}
	// destBytesPerLine is used as scanlinePad to ensure that no padding is required
	return new ImageData(width, height, srcData.depth, srcData.palette, srcData.scanlinePad, newData);
}
static ImageData flip(ImageData srcData, boolean vertical) {
	int bytesPerPixel = srcData.bytesPerLine / srcData.width;
	int destBytesPerLine = srcData.width * bytesPerPixel;
	byte[] newData = new byte[srcData.data.length];
	for (int srcY = 0; srcY < srcData.height; srcY++) {
		for (int srcX = 0; srcX < srcData.width; srcX++) {
			int destX = 0, destY = 0, destIndex = 0, srcIndex = 0;
			if (vertical){
				destX = srcX;
				destY = srcData.height - srcY - 1;
			} else {
				destX = srcData.width - srcX - 1;
				destY = srcY; 
			}
			destIndex = (destY * destBytesPerLine) + (destX * bytesPerPixel);
			srcIndex = (srcY * srcData.bytesPerLine) + (srcX * bytesPerPixel);
			System.arraycopy(srcData.data, srcIndex, newData, destIndex, bytesPerPixel);
		}
	}
	// destBytesPerLine is used as scanlinePad to ensure that no padding is required
	return new ImageData(srcData.width, srcData.height, srcData.depth, srcData.palette, srcData.scanlinePad, newData);
}

public static void main(String[] args) {
	Display display = new Display();
	
	// create an image with the word "hello" on it
	final Image image0 = new Image(display, 50, 30);
	GC gc = new GC(image0);
	gc.setBackground(display.getSystemColor(SWT.COLOR_RED));
	gc.fillRectangle(image0.getBounds());
	gc.drawString("hello",	5, 5, true);
	gc.dispose();
	
	ImageData data = image0.getImageData();
	// rotate and flip this image
	final Image image1 = new Image(display, rotate(data, SWT.LEFT));
	final Image image2 = new Image(display, rotate(data, SWT.RIGHT));
	final Image image3 = new Image(display, rotate(data, SWT.DOWN));
	final Image image4 = new Image(display, flip(data, true));
	final Image image5 = new Image(display, flip(data, false));

	Shell shell = new Shell(display);
	// draw the results on the shell
	shell.addPaintListener(new PaintListener(){
		public void paintControl(PaintEvent e) {
			e.gc.drawText("Original Image:", 10, 10, true);
			e.gc.drawImage(image0, 10, 40);
			e.gc.drawText("Left, Right, 180:", 10, 80, true);
			e.gc.drawImage(image1, 10, 110);
			e.gc.drawImage(image2, 50, 110);
			e.gc.drawImage(image3, 90, 110);
			e.gc.drawText("Flipped Vertical, Horizontal:", 10, 170, true);
			e.gc.drawImage(image4, 10, 200);
			e.gc.drawImage(image5, 70, 200);
		}
	});
	shell.setSize(300, 300);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	image0.dispose();
	image1.dispose();
	image2.dispose();
	image3.dispose();
	image4.dispose();
	image5.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10045, 1, '[SWT]SnippetSnippet14.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Control example snippet: detect mouse enter, exit and hover events
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet14 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setSize (100, 100);
	shell.addListener (SWT.MouseEnter, new Listener () {
		public void handleEvent (Event e) {
			System.out.println ("ENTER");
		}
	});
	shell.addListener (SWT.MouseExit, new Listener () {
		public void handleEvent (Event e) {
			System.out.println ("EXIT");
		}
	});
	shell.addListener (SWT.MouseHover, new Listener () {
		public void handleEvent (Event e) {
			System.out.println ("HOVER");
		}
	});
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10046, 1, '[SWT]SnippetSnippet140.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
  
/*
 * CoolBar example snippet: drop-down a chevron menu containing hidden tool items
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;

public class Snippet140 {
	static Display display;
	static Shell shell;
	static CoolBar coolBar;
	static Menu chevronMenu = null;
	
public static void main (String [] args) {
	display = new Display ();
	shell = new Shell (display);
	shell.setLayout(new GridLayout());
	coolBar = new CoolBar(shell, SWT.FLAT | SWT.BORDER);
	coolBar.setLayoutData(new GridData(GridData.FILL_BOTH));
	ToolBar toolBar = new ToolBar(coolBar, SWT.FLAT | SWT.WRAP);
	int minWidth = 0;
	for (int j = 0; j < 5; j++) {
		int width = 0;
		ToolItem item = new ToolItem(toolBar, SWT.PUSH);
		item.setText("B" + j);
		width = item.getWidth();
		/* find the width of the widest tool */
		if (width > minWidth) minWidth = width;
	}
	CoolItem coolItem = new CoolItem(coolBar, SWT.DROP_DOWN);
	coolItem.setControl(toolBar);
	Point size = toolBar.computeSize(SWT.DEFAULT, SWT.DEFAULT);
	Point coolSize = coolItem.computeSize (size.x, size.y);
	coolItem.setMinimumSize(minWidth, coolSize.y);
	coolItem.setPreferredSize(coolSize);
	coolItem.setSize(coolSize);
	coolItem.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent event) {
			if (event.detail == SWT.ARROW) {
				CoolItem item = (CoolItem) event.widget;
				Rectangle itemBounds = item.getBounds ();
				Point pt = coolBar.toDisplay(new Point(itemBounds.x, itemBounds.y));
				itemBounds.x = pt.x;
				itemBounds.y = pt.y;
				ToolBar bar = (ToolBar) item.getControl ();
				ToolItem[] tools = bar.getItems ();
				
				int i = 0;
				while (i < tools.length) {
					Rectangle toolBounds = tools[i].getBounds ();
					pt = bar.toDisplay(new Point(toolBounds.x, toolBounds.y));
					toolBounds.x = pt.x;
					toolBounds.y = pt.y;
					
					/* Figure out the visible portion of the tool by looking at the
					 * intersection of the tool bounds with the cool item bounds. */
			  		Rectangle intersection = itemBounds.intersection (toolBounds);
			  		
					/* If the tool is not completely within the cool item bounds, then it
					 * is partially hidden, and all remaining tools are completely hidden. */
			  		if (!intersection.equals (toolBounds)) break;
			  		i++;
				}
				
				/* Create a menu with items for each of the completely hidden buttons. */
				if (chevronMenu != null) chevronMenu.dispose();
				chevronMenu = new Menu (coolBar);
				for (int j = i; j < tools.length; j++) {
					MenuItem menuItem = new MenuItem (chevronMenu, SWT.PUSH);
					menuItem.setText (tools[j].getText());
				}
				
				/* Drop down the menu below the chevron, with the left edges aligned. */
				pt = coolBar.toDisplay(new Point(event.x, event.y));
				chevronMenu.setLocation (pt.x, pt.y);
				chevronMenu.setVisible (true);
			}
		}
	});

	shell.pack();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10047, 1, '[SWT]SnippetSnippet141.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Image example snippet: display an animated GIF
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet141 {
	static Display display;
	static Shell shell;
	static GC shellGC;
	static Color shellBackground;
	static ImageLoader loader;
	static ImageData[] imageDataArray;
	static Thread animateThread;
	static Image image;
	static final boolean useGIFBackground = false;
	
	public static void main(String[] args) {
		display = new Display();
		shell = new Shell(display);
		shell.setSize(300, 300);
		shell.open();
		shellGC = new GC(shell);
		shellBackground = shell.getBackground();

		FileDialog dialog = new FileDialog(shell);
		dialog.setFilterExtensions(new String[] {"*.gif"});
		String fileName = dialog.open();
		if (fileName != null) {
			loader = new ImageLoader();
			try {
				imageDataArray = loader.load(fileName);
				if (imageDataArray.length > 1) {
					animateThread = new Thread("Animation") {
						public void run() {
							/* Create an off-screen image to draw on, and fill it with the shell background. */
							Image offScreenImage = new Image(display, loader.logicalScreenWidth, loader.logicalScreenHeight);
							GC offScreenImageGC = new GC(offScreenImage);
							offScreenImageGC.setBackground(shellBackground);
							offScreenImageGC.fillRectangle(0, 0, loader.logicalScreenWidth, loader.logicalScreenHeight);
								
							try {
								/* Create the first image and draw it on the off-screen image. */
								int imageDataIndex = 0;	
								ImageData imageData = imageDataArray[imageDataIndex];
								if (image != null && !image.isDisposed()) image.dispose();
								image = new Image(display, imageData);
								offScreenImageGC.drawImage(
									image,
									0,
									0,
									imageData.width,
									imageData.height,
									imageData.x,
									imageData.y,
									imageData.width,
									imageData.height);

								/* Now loop through the images, creating and drawing each one
								 * on the off-screen image before drawing it on the shell. */
								int repeatCount = loader.repeatCount;
								while (loader.repeatCount == 0 || repeatCount > 0) {
									switch (imageData.disposalMethod) {
									case SWT.DM_FILL_BACKGROUND:
										/* Fill with the background color before drawing. */
										Color bgColor = null;
										if (useGIFBackground && loader.backgroundPixel != -1) {
											bgColor = new Color(display, imageData.palette.getRGB(loader.backgroundPixel));
										}
										offScreenImageGC.setBackground(bgColor != null ? bgColor : shellBackground);
										offScreenImageGC.fillRectangle(imageData.x, imageData.y, imageData.width, imageData.height);
										if (bgColor != null) bgColor.dispose();
										break;
									case SWT.DM_FILL_PREVIOUS:
										/* Restore the previous image before drawing. */
										offScreenImageGC.drawImage(
											image,
											0,
											0,
											imageData.width,
											imageData.height,
											imageData.x,
											imageData.y,
											imageData.width,
											imageData.height);
										break;
									}
														
									imageDataIndex = (imageDataIndex + 1) % imageDataArray.length;
									imageData = imageDataArray[imageDataIndex];
									image.dispose();
									image = new Image(display, imageData);
									offScreenImageGC.drawImage(
										image,
										0,
										0,
										imageData.width,
										imageData.height,
										imageData.x,
										imageData.y,
										imageData.width,
										imageData.height);
									
									/* Draw the off-screen image to the shell. */
									shellGC.drawImage(offScreenImage, 0, 0);
									
									/* Sleep for the specified delay time (adding commonly-used slow-down fudge factors). */
									try {
										int ms = imageData.delayTime * 10;
										if (ms < 20) ms += 30;
										if (ms < 30) ms += 10;
										Thread.sleep(ms);
									} catch (InterruptedException e) {
									}
									
									/* If we have just drawn the last image, decrement the repeat count and start again. */
									if (imageDataIndex == imageDataArray.length - 1) repeatCount--;
								}
							} catch (SWTException ex) {
								System.out.println("There was an error animating the GIF");
							} finally {
								if (offScreenImage != null && !offScreenImage.isDisposed()) offScreenImage.dispose();
								if (offScreenImageGC != null && !offScreenImageGC.isDisposed()) offScreenImageGC.dispose();
								if (image != null && !image.isDisposed()) image.dispose();
							}
						}
					};
					animateThread.setDaemon(true);
					animateThread.start();
				}
			} catch (SWTException ex) {
				System.out.println("There was an error loading the GIF");
			}
		}

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10048, 1, '[SWT]SnippetSnippet142.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
  
/*
 * UI Automation (for testing tools) snippet: post mouse events
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet142 {

public static void main(String[] args) {
	final Display display = new Display();
	final Shell shell = new Shell(display);
	final Button button = new Button(shell,SWT.NONE);
	button.setSize(100,100);
	button.setText("Click");
	shell.pack();
	shell.open();
	button.addListener(SWT.MouseDown, new Listener() {
		public void handleEvent(Event e){
			System.out.println("Mouse Down (button: " + e.button + " x: " + e.x + " y: " + e.y + ")");
		}
	});
	final Point pt = display.map(shell, null, 50, 50);
	new Thread(){
		Event event;
		public void run(){
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {}
			event = new Event();
			event.type = SWT.MouseMove;
			event.x = pt.x;
			event.y = pt.y;
			display.post(event);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {}
			event.type = SWT.MouseDown;
			event.button = 1;
			display.post(event);
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {}
			event.type = SWT.MouseUp;
			display.post(event);
		}	
	}.start();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10049, 1, '[SWT]SnippetSnippet143.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
  
/*
 * Tray example snippet: place an icon with a popup menu on the system tray
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet143 {

public static void main(String[] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Image image = new Image (display, 16, 16);
	final Tray tray = display.getSystemTray ();
	if (tray == null) {
		System.out.println ("The system tray is not available");
	} else {
		final TrayItem item = new TrayItem (tray, SWT.NONE);
		item.setToolTipText("SWT TrayItem");
		item.addListener (SWT.Show, new Listener () {
			public void handleEvent (Event event) {
				System.out.println("show");
			}
		});
		item.addListener (SWT.Hide, new Listener () {
			public void handleEvent (Event event) {
				System.out.println("hide");
			}
		});
		item.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event event) {
				System.out.println("selection");
			}
		});
		item.addListener (SWT.DefaultSelection, new Listener () {
			public void handleEvent (Event event) {
				System.out.println("default selection");
			}
		});
		final Menu menu = new Menu (shell, SWT.POP_UP);
		for (int i = 0; i < 8; i++) {
			MenuItem mi = new MenuItem (menu, SWT.PUSH);
			mi.setText ("Item" + i);
			mi.addListener (SWT.Selection, new Listener () {
				public void handleEvent (Event event) {
					System.out.println("selection " + event.widget);
				}
			});
			if (i == 0) menu.setDefaultItem(mi);
		}
		item.addListener (SWT.MenuDetect, new Listener () {
			public void handleEvent (Event event) {
				menu.setVisible (true);
			}
		});
		item.setImage (image);
	}
	shell.setBounds(50, 50, 300, 200);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	image.dispose ();
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10050, 1, '[SWT]SnippetSnippet144.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Virtual Table example snippet: create a table with 1,000,000 items (lazy)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet144 {

static final int COUNT = 1000000;

public static void main(String[] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setLayout (new RowLayout (SWT.VERTICAL));
	final Table table = new Table (shell, SWT.VIRTUAL | SWT.BORDER);
	table.addListener (SWT.SetData, new Listener () {
		public void handleEvent (Event event) {
			TableItem item = (TableItem) event.item;
			int index = table.indexOf (item);
			item.setText ("Item " + index);
			System.out.println (item.getText ());
		}
	});
	table.setLayoutData (new RowData (200, 200));
	Button button = new Button (shell, SWT.PUSH);
	button.setText ("Add Items");
	final Label label = new Label(shell, SWT.NONE);
	button.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event event) {
			long t1 = System.currentTimeMillis ();
			table.setItemCount (COUNT);
			long t2 = System.currentTimeMillis ();
			label.setText ("Items: " + COUNT + ", Time: " + (t2 - t1) + " (ms)");
			shell.layout ();
		}
	});
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10051, 1, '[SWT]SnippetSnippet145.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * TextLayout example snippet: draw internationalized styled text on a shell
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

public class Snippet145 {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell (display);
		
		Font font1 = new Font(display, "Tahoma", 14, SWT.BOLD);
		Font font2 = new Font(display, "MS Mincho", 20, SWT.ITALIC);
		Font font3 = new Font(display, "Arabic Transparent", 18, SWT.NORMAL);
		
		Color blue = display.getSystemColor(SWT.COLOR_BLUE);
		Color green = display.getSystemColor(SWT.COLOR_GREEN);
		Color yellow = display.getSystemColor(SWT.COLOR_YELLOW);
		Color gray = display.getSystemColor(SWT.COLOR_GRAY);
		
		final TextLayout layout = new TextLayout(display);
		TextStyle style1 = new TextStyle(font1, yellow, blue);
		TextStyle style2 = new TextStyle(font2, green, null);
		TextStyle style3 = new TextStyle(font3, blue, gray);
		
		layout.setText("English \u65E5\u672C\u8A9E  \u0627\u0644\u0639\u0631\u0628\u064A\u0651\u0629");
		layout.setStyle(style1, 0, 6);
		layout.setStyle(style2, 8, 10);
		layout.setStyle(style3, 13, 21);
					
		shell.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		shell.addListener(SWT.Paint, new Listener() {
			public void handleEvent (Event event) {
				layout.draw(event.gc, 10, 10);
			}
		});
		shell.open();	
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		font1.dispose();
		font2.dispose();
		font3.dispose();
		layout.dispose();
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10052, 1, '[SWT]SnippetSnippet146.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
  
/*
 * UI Automation (for testing tools) snippet: post key events
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet146 {

public static void main(String[] args) {
	final Display display = new Display();
	final Shell shell = new Shell(display);
	final Text text = new Text(shell, SWT.BORDER);
	text.setSize(text.computeSize(150, SWT.DEFAULT));
	shell.pack();
	shell.open();
	new Thread(){
		public void run(){
			String string = "Love the method.";
			for (int i = 0; i < string.length(); i++) {
				char ch = string.charAt(i);
				boolean shift = Character.isUpperCase(ch);
				ch = Character.toLowerCase(ch);
				if (shift) {
					Event event = new Event();
					event.type = SWT.KeyDown;
					event.keyCode = SWT.SHIFT;
					display.post(event);	
				}
				Event event = new Event();
				event.type = SWT.KeyDown;
				event.character = ch;
				display.post(event);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {}
				event.type = SWT.KeyUp;
				display.post(event);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
				if (shift) {
					event = new Event();
					event.type = SWT.KeyUp;
					event.keyCode = SWT.SHIFT;
					display.post(event);	
				}
			}
		}	
	}.start();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10053, 1, '[SWT]SnippetSnippet147.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Combo example snippet: stop CR from going to the default button
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet147  {
public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new GridLayout());
	Combo combo = new Combo(shell, SWT.NONE);
	combo.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
	combo.setText("Here is some text");
	combo.addSelectionListener(new SelectionAdapter() {
		public void widgetDefaultSelected(SelectionEvent e) {
			System.out.println("Combo default selected (overrides default button)");
		}
	});
	combo.addTraverseListener(new TraverseListener() {
		public void keyTraversed(TraverseEvent e) {
			if (e.detail == SWT.TRAVERSE_RETURN) {
				e.doit = false;
				e.detail = SWT.TRAVERSE_NONE;
			}
		}
	});
	Button button = new Button(shell, SWT.PUSH);
	button.setText("Ok");
	button.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			System.out.println("Button selected");
		}
	});
	shell.setDefaultButton(button);
	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10054, 1, '[SWT]SnippetSnippet148.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Text example snippet: check if the browser is available or not
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.browser.*;

public class Snippet148 {

	public static void main(String[] args) {
		final Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		Browser browser = null;
		try {
			browser = new Browser(shell, SWT.NONE);
		} catch (SWTError e) {
			/* The Browser widget throws an SWTError if it fails to
			 * instantiate properly. Application code should catch
			 * this SWTError and disable any feature requiring the
			 * Browser widget.
			 * Platform requirements for the SWT Browser widget are available
			 * from the SWT FAQ website. 
			 */
		}
		if (browser != null) {
			/* The Browser widget can be used */
			browser.setUrl("http://www.eclipse.org");
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}


', now(), now());
insert into SNIPPET values (10055, 1, '[SWT]SnippetSnippet149.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * TableEditor example snippet: place a progress bar in a table
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.custom.*;

public class Snippet149 {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout (new FillLayout ());
		Table table = new Table (shell, SWT.BORDER);
		table.setHeaderVisible (true);
		table.setLinesVisible(true);
		for (int i=0; i<2; i++) {
			new TableColumn (table, SWT.NONE);
		}
		table.getColumn (0).setText ("Task");
		table.getColumn (1).setText ("Progress");
		for (int i=0; i<40; i++) {
			TableItem item = new TableItem (table, SWT.NONE);
			item.setText ("Task " + i);
			if ( i % 5 == 0) {
				ProgressBar bar = new ProgressBar (table, SWT.NONE);
				bar.setSelection (i);
				TableEditor editor = new TableEditor (table);
				editor.grabHorizontal = editor.grabVertical = true;
				editor.setEditor (bar, item, 1);
			}
		}
		table.getColumn (0).pack ();
		table.getColumn (1).setWidth (128);
		shell.pack ();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10056, 1, '[SWT]SnippetSnippet15.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Tree example snippet: create a tree
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet15 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	final Tree tree = new Tree (shell, SWT.BORDER);
	for (int i=0; i<4; i++) {
		TreeItem iItem = new TreeItem (tree, 0);
		iItem.setText ("TreeItem (0) -" + i);
		for (int j=0; j<4; j++) {
			TreeItem jItem = new TreeItem (iItem, 0);
			jItem.setText ("TreeItem (1) -" + j);
			for (int k=0; k<4; k++) {
				TreeItem kItem = new TreeItem (jItem, 0);
				kItem.setText ("TreeItem (2) -" + k);
				for (int l=0; l<4; l++) {
					TreeItem lItem = new TreeItem (kItem, 0);
					lItem.setText ("TreeItem (3) -" + l);
				}
			}
		}
	}
	shell.setSize (200, 200);
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10057, 1, '[SWT]SnippetSnippet150.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * CoolBar example snippet: create a coolbar (relayout when resized)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet150 {

static int itemCount;
static CoolItem createItem(CoolBar coolBar, int count) {
    ToolBar toolBar = new ToolBar(coolBar, SWT.FLAT);
    for (int i = 0; i < count; i++) {
        ToolItem item = new ToolItem(toolBar, SWT.PUSH);
        item.setText(itemCount++ +"");
    }
    toolBar.pack();
    Point size = toolBar.getSize();
    CoolItem item = new CoolItem(coolBar, SWT.NONE);
    item.setControl(toolBar);
    Point preferred = item.computeSize(size.x, size.y);
    item.setPreferredSize(preferred);
    return item;
}

public static void main(String[] args) {
    Display display = new Display();
    final Shell shell = new Shell(display);
    CoolBar coolBar = new CoolBar(shell, SWT.NONE);
    createItem(coolBar, 3);
    createItem(coolBar, 2);
    createItem(coolBar, 3);
    createItem(coolBar, 4);
    int style = SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL;
    Text text = new Text(shell, style);
    FormLayout layout = new FormLayout();
    shell.setLayout(layout);
    FormData coolData = new FormData();
    coolData.left = new FormAttachment(0);
    coolData.right = new FormAttachment(100);
    coolData.top = new FormAttachment(0);
    coolBar.setLayoutData(coolData);
    coolBar.addListener(SWT.Resize, new Listener() {
        public void handleEvent(Event event) {
            shell.layout();
        }
    });
    FormData textData = new FormData();
    textData.left = new FormAttachment(0);
    textData.right = new FormAttachment(100);
    textData.top = new FormAttachment(coolBar);
    textData.bottom = new FormAttachment(100);
    text.setLayoutData(textData);
    shell.open();
    while (!shell.isDisposed()) {
        if (!display.readAndDispatch()) display.sleep();
    }
    display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10058, 1, '[SWT]SnippetSnippet151.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Create a virtual table and add 1000 entries to it every 500 ms.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import java.util.*;
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet151 {

static int[] data = new int[0];

public static void main (String [] args) {
	final Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	final Table table = new Table(shell, SWT.BORDER | SWT.VIRTUAL);
	table.addListener(SWT.SetData, new Listener() {
		public void handleEvent(Event e) {
			TableItem item = (TableItem)e.item;
			int index = table.indexOf(item);
			item.setText("Item "+data[index]);
		}
	});
	Thread thread = new Thread() {
		public void run() {
			int count = 0;
			Random random = new Random();
			while (count++ < 500) {
				if (table.isDisposed()) return;
				// add 10 random numbers to array and sort
				int grow = 10;
				int[] newData = new int[data.length + grow];
				System.arraycopy(data, 0, newData, 0, data.length);
				int index = data.length;
				data = newData;
				for (int j = 0; j < grow; j++) {
					data[index++] = random.nextInt();
				}
				Arrays.sort(data);
				display.syncExec(new Runnable() {
					public void run() {
						if (table.isDisposed()) return;
						table.setItemCount(data.length);
						table.clearAll();
					}
				});
				try {Thread.sleep(500);} catch (Throwable t) {}
			}
		}
	};
	thread.start();
	shell.open ();
	while (!shell.isDisposed() || thread.isAlive()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10059, 1, '[SWT]SnippetSnippet152.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Control example snippet: update a status line when an item is armed
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet152 {

public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);
    FormLayout layout = new FormLayout();
    shell.setLayout(layout);
    final Label label = new Label(shell, SWT.BORDER);
    Listener armListener = new Listener() {
        public void handleEvent(Event event) {
            MenuItem item = (MenuItem) event.widget;
            label.setText(item.getText());
            label.update();
        }
    };
    Listener showListener = new Listener() {
        public void handleEvent(Event event) {
            Menu menu = (Menu) event.widget;
            MenuItem item = menu.getParentItem();
            if (item != null) {
                label.setText(item.getText());
                label.update();
            }
        }
    };
    Listener hideListener = new Listener() {
        public void handleEvent(Event event) {
            label.setText("");
            label.update();
        }
    };
    FormData labelData = new FormData();
    labelData.left = new FormAttachment(0);
    labelData.right = new FormAttachment(100);
    labelData.bottom = new FormAttachment(100);
    label.setLayoutData(labelData);
    Menu menuBar = new Menu(shell, SWT.BAR);
    shell.setMenuBar(menuBar);
    MenuItem fileItem = new MenuItem(menuBar, SWT.CASCADE);
    fileItem.setText("File");
    fileItem.addListener(SWT.Arm, armListener);
    MenuItem editItem = new MenuItem(menuBar, SWT.CASCADE);
    editItem.setText("Edit");
    editItem.addListener(SWT.Arm, armListener);
    Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
    fileMenu.addListener(SWT.Hide, hideListener);
    fileMenu.addListener(SWT.Show, showListener);
    fileItem.setMenu(fileMenu);
    String[] fileStrings = { "New", "Close", "Exit" };
    for (int i = 0; i < fileStrings.length; i++) {
        MenuItem item = new MenuItem(fileMenu, SWT.PUSH);
        item.setText(fileStrings[i]);
        item.addListener(SWT.Arm, armListener);
    }
    Menu editMenu = new Menu(shell, SWT.DROP_DOWN);
    editMenu.addListener(SWT.Hide, hideListener);
    editMenu.addListener(SWT.Show, showListener);
    String[] editStrings = { "Cut", "Copy", "Paste" };
    editItem.setMenu(editMenu);
    for (int i = 0; i < editStrings.length; i++) {
        MenuItem item = new MenuItem(editMenu, SWT.PUSH);
        item.setText(editStrings[i]);
        item.addListener(SWT.Arm, armListener);
    }
    shell.open();
    while (!shell.isDisposed()) {
        if (!display.readAndDispatch()) display.sleep();
    }
    display.dispose();
}

}
', now(), now());
insert into SNIPPET values (10060, 1, '[SWT]SnippetSnippet153.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * ToolBar example snippet: update a status line when the pointer enters a ToolItem
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet153 {
	
static String statusText = "";
public static void main(String[] args) {
    final Display display = new Display();
    Shell shell = new Shell(display);
    shell.setBounds(10, 10, 200, 200);
    final ToolBar bar = new ToolBar(shell, SWT.BORDER);
    bar.setBounds(10, 10, 150, 50);
    final Label statusLine = new Label(shell, SWT.BORDER);
    statusLine.setBounds(10, 90, 150, 30);
    new ToolItem(bar, SWT.NONE).setText("item 1");
    new ToolItem(bar, SWT.NONE).setText("item 2");
    new ToolItem(bar, SWT.NONE).setText("item 3");
    bar.addMouseMoveListener(new MouseMoveListener() {
        public void mouseMove(MouseEvent e) {
            ToolItem item = bar.getItem(new Point(e.x, e.y));
            String name = "";
            if (item != null) {
                name = item.getText();
            }
            if (!statusText.equals(name)) {
                statusLine.setText(name);
                statusText = name;
            }
        }
    });
    shell.open();
    while (!shell.isDisposed()) {
        if (!display.readAndDispatch()) display.sleep();
    }
    display.dispose();
}

}
', now(), now());
insert into SNIPPET values (10061, 1, '[SWT]SnippetSnippet154.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * example snippet: embed a JTable in SWT (no flicker)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Panel;
import javax.swing.*;
import java.util.Vector;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.awt.SWT_AWT;

public class Snippet154 {

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		Composite composite = new Composite(shell, SWT.NO_BACKGROUND | SWT.EMBEDDED);
		
		/*
		* Set a Windows specific AWT property that prevents heavyweight
		* components from erasing their background. Note that this
		* is a global property and cannot be scoped. It might not be
		* suitable for your application.
		*/
		try {
			System.setProperty("sun.awt.noerasebackground","true");
		} catch (NoSuchMethodError error) {}

		/* Create and setting up frame */
		Frame frame = SWT_AWT.new_Frame(composite);
		Panel panel = new Panel(new BorderLayout()) {
			public void update(java.awt.Graphics g) {
				/* Do not erase the background */ 
				paint(g);
			}
		};
		frame.add(panel);
		JRootPane root = new JRootPane();
		panel.add(root);
		java.awt.Container contentPane = root.getContentPane();

		/* Creating components */
		int nrows = 1000, ncolumns = 10;
		Vector rows = new Vector();
		for (int i = 0; i < nrows; i++) {
			Vector row = new Vector();
			for (int j = 0; j < ncolumns; j++) {
				row.addElement("Item " + i + "-" + j);
			}
			rows.addElement(row);
		}
		Vector columns = new Vector();
		for (int i = 0; i < ncolumns; i++) {
			columns.addElement("Column " + i);
		}
		JTable table = new JTable(rows, columns);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.createDefaultColumnsFromModel();
		JScrollPane scrollPane = new JScrollPane(table);
		contentPane.setLayout(new BorderLayout());
		contentPane.add(scrollPane);
		
		shell.open();
		while(!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10062, 1, '[SWT]SnippetSnippet155.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * example snippet: draw an X using AWT Graphics
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import java.awt.Frame;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Dimension;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.awt.SWT_AWT;

public class Snippet155 {

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		Composite composite = new Composite(shell, SWT.EMBEDDED);
		
		/* Draw an X using AWT */
		Frame frame = SWT_AWT.new_Frame(composite);
		Canvas canvas = new Canvas() {
			public void paint (Graphics g) {
				Dimension d = getSize();
				g.drawLine(0, 0, d.width, d.height);
				g.drawLine(d.width, 0, 0, d.height);
			}
		};
		frame.add(canvas);

		shell.open();
		while(!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10063, 1, '[SWT]SnippetSnippet156.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * example snippet: convert between SWT Image and AWT BufferedImage
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.DirectColorModel;
import java.awt.image.IndexColorModel;
import java.awt.image.WritableRaster;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

public class Snippet156 {
	
static BufferedImage convertToAWT(ImageData data) {
	ColorModel colorModel = null;
	PaletteData palette = data.palette;
	if (palette.isDirect) {
		colorModel = new DirectColorModel(data.depth, palette.redMask, palette.greenMask, palette.blueMask);
		BufferedImage bufferedImage = new BufferedImage(colorModel, colorModel.createCompatibleWritableRaster(data.width, data.height), false, null);
		WritableRaster raster = bufferedImage.getRaster();
		int[] pixelArray = new int[3];
		for (int y = 0; y < data.height; y++) {
			for (int x = 0; x < data.width; x++) {
				int pixel = data.getPixel(x, y);
				RGB rgb = palette.getRGB(pixel);
				pixelArray[0] = rgb.red;
				pixelArray[1] = rgb.green;
				pixelArray[2] = rgb.blue;
				raster.setPixels(x, y, 1, 1, pixelArray);
			}
		}
		return bufferedImage;
	} else {
		RGB[] rgbs = palette.getRGBs();
		byte[] red = new byte[rgbs.length];
		byte[] green = new byte[rgbs.length];
		byte[] blue = new byte[rgbs.length];
		for (int i = 0; i < rgbs.length; i++) {
			RGB rgb = rgbs[i];
			red[i] = (byte)rgb.red;
			green[i] = (byte)rgb.green;
			blue[i] = (byte)rgb.blue;
		}
		if (data.transparentPixel != -1) {
			colorModel = new IndexColorModel(data.depth, rgbs.length, red, green, blue, data.transparentPixel);
		} else {
			colorModel = new IndexColorModel(data.depth, rgbs.length, red, green, blue);
		}		
		BufferedImage bufferedImage = new BufferedImage(colorModel, colorModel.createCompatibleWritableRaster(data.width, data.height), false, null);
		WritableRaster raster = bufferedImage.getRaster();
		int[] pixelArray = new int[1];
		for (int y = 0; y < data.height; y++) {
			for (int x = 0; x < data.width; x++) {
				int pixel = data.getPixel(x, y);
				pixelArray[0] = pixel;
				raster.setPixel(x, y, pixelArray);
			}
		}
		return bufferedImage;
	}
}

static ImageData convertToSWT(BufferedImage bufferedImage) {
	if (bufferedImage.getColorModel() instanceof DirectColorModel) {
		DirectColorModel colorModel = (DirectColorModel)bufferedImage.getColorModel();
		PaletteData palette = new PaletteData(colorModel.getRedMask(), colorModel.getGreenMask(), colorModel.getBlueMask());
		ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(), colorModel.getPixelSize(), palette);
		WritableRaster raster = bufferedImage.getRaster();
		int[] pixelArray = new int[3];
		for (int y = 0; y < data.height; y++) {
			for (int x = 0; x < data.width; x++) {
				raster.getPixel(x, y, pixelArray);
				int pixel = palette.getPixel(new RGB(pixelArray[0], pixelArray[1], pixelArray[2]));
				data.setPixel(x, y, pixel);
			}
		}		
		return data;		
	} else if (bufferedImage.getColorModel() instanceof IndexColorModel) {
		IndexColorModel colorModel = (IndexColorModel)bufferedImage.getColorModel();
		int size = colorModel.getMapSize();
		byte[] reds = new byte[size];
		byte[] greens = new byte[size];
		byte[] blues = new byte[size];
		colorModel.getReds(reds);
		colorModel.getGreens(greens);
		colorModel.getBlues(blues);
		RGB[] rgbs = new RGB[size];
		for (int i = 0; i < rgbs.length; i++) {
			rgbs[i] = new RGB(reds[i] & 0xFF, greens[i] & 0xFF, blues[i] & 0xFF);
		}
		PaletteData palette = new PaletteData(rgbs);
		ImageData data = new ImageData(bufferedImage.getWidth(), bufferedImage.getHeight(), colorModel.getPixelSize(), palette);
		data.transparentPixel = colorModel.getTransparentPixel();
		WritableRaster raster = bufferedImage.getRaster();
		int[] pixelArray = new int[1];
		for (int y = 0; y < data.height; y++) {
			for (int x = 0; x < data.width; x++) {
				raster.getPixel(x, y, pixelArray);
				data.setPixel(x, y, pixelArray[0]);
			}
		}
		return data;
	}
	return null;
}

static ImageData createSampleImage(Display display) {
	Image image = new Image(display, 100, 100);
	Rectangle bounds = image.getBounds();
	GC gc = new GC(image);
	gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
	gc.fillRectangle(bounds);
	gc.setBackground(display.getSystemColor(SWT.COLOR_GREEN));
	gc.fillOval(0, 0, bounds.width, bounds.height);
	gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
	gc.drawLine(0, 0, bounds.width, bounds.height);
	gc.drawLine(bounds.width, 0, 0, bounds.height);
	gc.dispose();
	ImageData data = image.getImageData();
	image.dispose();
	return data;
}

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setText("SWT Image");
	ImageData data;
	if (args.length > 0) {
		String fileName = args[0];
		data = new ImageData(fileName);
	} else {
		data = createSampleImage(display);
	}
	final Image swtImage = new Image(display, data);
	final BufferedImage awtImage = convertToAWT(data);
	final Image swtImage2 = new Image(display, convertToSWT(awtImage));
	shell.addListener(SWT.Paint, new Listener() {
		public void handleEvent(Event e) {
			int y = 10;
			if (swtImage != null) {
				e.gc.drawImage(swtImage, 10, y);
				y += swtImage.getBounds().height + 10;
			}
			if (swtImage2 != null) {
				e.gc.drawImage(swtImage2, 10, y);
			}
		}
	});
	Frame frame = new Frame() {
		public void paint(Graphics g) {
			Insets insets = getInsets();
			if (awtImage != null) {
				g.drawImage(awtImage, 10 + insets.left, 10 + insets.top, null);
			}
		}
	};
	frame.setTitle("AWT Image");
	shell.setLocation(50, 50);
	Rectangle bounds = swtImage.getBounds();
	shell.setSize(bounds.width + 50, bounds.height * 2 + 100);
	Point size = shell.getSize();
	Point location = shell.getLocation();
	Insets insets = frame.getInsets();
	frame.setLocation(location.x + size.x + 10, location.y);
	frame.setSize(size.x - (insets.left + insets.right), size.y - (insets.top + insets.bottom));
	frame.setVisible(true);	
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	if (swtImage != null) swtImage.dispose();
	if (swtImage2 != null) swtImage.dispose();
	frame.dispose();
	display.dispose();
	/* Note: If you are using JDK 1.3.x, you need to use System.exit(0) at the end of your program to exit AWT.
	 * This is because in 1.3.x, AWT does not exit when the frame is disposed, because the AWT thread is not a daemon.
	 * This was fixed in JDK 1.4.x with the addition of the AWT Shutdown thread.
	 */
}
}
', now(), now());
insert into SNIPPET values (10064, 1, '[SWT]SnippetSnippet157.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * example snippet: Embed Word in an applet (win32 only)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
	
import java.applet.*;
	 
public class Snippet157 extends Applet {
	
	org.eclipse.swt.widgets.Display display;
	org.eclipse.swt.widgets.Shell swtParent;
	java.awt.Canvas awtParent;

public void init () {
	Thread thread = new Thread (new Runnable () {
		public void run () {
			setLayout(new java.awt.GridLayout (1, 1));
			awtParent = new java.awt.Canvas ();
			add (awtParent);
			display = new org.eclipse.swt.widgets.Display ();
			swtParent = org.eclipse.swt.awt.SWT_AWT.new_Shell (display, awtParent);
			swtParent.setLayout (new org.eclipse.swt.layout.FillLayout ());
			org.eclipse.swt.ole.win32.OleFrame frame = new org.eclipse.swt.ole.win32.OleFrame (swtParent, org.eclipse.swt.SWT.NONE);
			org.eclipse.swt.ole.win32.OleClientSite site;
			try {
				site = new org.eclipse.swt.ole.win32.OleClientSite (frame, org.eclipse.swt.SWT.NONE, "Word.Document");
			} catch (org.eclipse.swt.SWTException e) {
				String str = "Create OleClientSite Error" + e.toString ();
				System.out.println (str);
				return;
			}
			setSize (500, 500);
			validate ();
			site.doVerb (org.eclipse.swt.ole.win32.OLE.OLEIVERB_SHOW);
			
			while (swtParent != null && !swtParent.isDisposed ()) {
				if (!display.readAndDispatch ()) display.sleep ();		
			}	
		}
	});
	thread.start ();
}
 public void stop (){
 	if (display != null && !display.isDisposed ()){
 		display.syncExec(new Runnable () {
 			public void run () {
 				if (swtParent != null && !swtParent.isDisposed ()) swtParent.dispose ();
 				swtParent = null;
 				display.dispose ();
 				display = null;
 			}
 		});
 		remove (awtParent);
 		awtParent = null;
 	}
 }
}', now(), now());
insert into SNIPPET values (10065, 1, '[SWT]SnippetSnippet158.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Drag and Drop example snippet: determine native data types available (motif only)
 * NOTE: This snippet uses internal SWT packages that are
 * subject to change without notice.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.internal.*;
import org.eclipse.swt.internal.motif.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet158 extends ByteArrayTransfer {

private static Snippet158 _instance = new Snippet158();
private int[] ids;
private String[] names;
	
public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	final Table control = new Table(shell, SWT.NONE);
	TableItem item = new TableItem(control, SWT.NONE);
	item.setText("Drag data over this site to see the native transfer type.");
	DropTarget target = new DropTarget(control, DND.DROP_DEFAULT | DND.DROP_COPY | DND.DROP_LINK | DND.DROP_MOVE);
	target.setTransfer(new Transfer[] {Snippet158.getInstance()});
	target.addDropListener(new DropTargetAdapter() {
		public void dragEnter(DropTargetEvent event) {			
			String ops = "";
			if ((event.operations & DND.DROP_COPY) != 0) ops += "Copy;";
			if ((event.operations & DND.DROP_MOVE) != 0) ops += "Move;";
			if ((event.operations & DND.DROP_LINK) != 0) ops += "Link;";
			control.removeAll();
			TableItem item1 = new TableItem(control,SWT.NONE);
			item1.setText("Allowed Operations are "+ops);
			
			if (event.detail == DND.DROP_DEFAULT) {
				if ((event.operations & DND.DROP_COPY) != 0) {
					event.detail = DND.DROP_COPY;
				} else if ((event.operations & DND.DROP_LINK) != 0) {
					event.detail = DND.DROP_LINK;
				} else if ((event.operations & DND.DROP_MOVE) != 0) {
					event.detail = DND.DROP_MOVE;
				}
			}
			
			TransferData[] data = event.dataTypes;
			for (int i = 0; i < data.length; i++) {
				int id = data[i].type;
				String name = getNameFromId(id);
				TableItem item2 = new TableItem(control,SWT.NONE);
				item2.setText("Data type is "+id+" "+name);
			}
		}
	});
	
	shell.setSize(400, 400);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}

public static Snippet158 getInstance () {
	return _instance;
}
Snippet158() {
}
public void javaToNative (Object object, TransferData transferData) {
}
public Object nativeToJava(TransferData transferData){
	return "Hello World";
}
protected String[] getTypeNames(){
	return names;
}
static int shellHandle;
protected int[] getTypeIds(){
	if (ids == null) {
		Display display = Display.getCurrent();
		int widgetClass = OS.topLevelShellWidgetClass ();
		shellHandle = OS.XtAppCreateShell (null, null, widgetClass, display.xDisplay, null, 0);
		OS.XtSetMappedWhenManaged (shellHandle, false);
		OS.XtRealizeWidget (shellHandle);
		
		ids = new int[840];
		names = new String[840];
		for (int i = 0; i < ids.length; i++) {
			ids[i] = i+i;
			names[i] = getNameFromId(i+1);
		}
	}
	return ids;
}
static String getNameFromId(int id) {
	int xDisplay = OS.XtDisplay (shellHandle);
	int ptr = 0;
	try {
		ptr = OS.XmGetAtomName(xDisplay, id);
	} catch (Throwable t) {
	}
	if (ptr == 0) return "invalid "+id;
	int length = OS.strlen(ptr);
	byte[] nameBuf = new byte[length];
	OS.memmove(nameBuf, ptr, length);
	OS.XFree(ptr);
	return new String(Converter.mbcsToWcs(null, nameBuf)).toLowerCase();
}
}
', now(), now());
insert into SNIPPET values (10066, 1, '[SWT]SnippetSnippet159.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Browser example snippet: modify HTML title tag
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet159 {
	public static void main(String [] args) {
		final String newTitle = "New Value for Title";
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		final Browser browser;
		try {
			browser = new Browser(shell, SWT.NONE);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			return;
		}
		browser.addTitleListener(new TitleListener() {
			public void changed(TitleEvent event) {
				System.out.println("TitleEvent: "+event.title);
				shell.setText(event.title);
			}
		});
		browser.addProgressListener(new ProgressListener() {
			public void changed(ProgressEvent event) {
			}
			public void completed(ProgressEvent event) {
				/* Set HTML title tag using JavaScript and DOM when page has been loaded */
				boolean result = browser.execute("document.title="+newTitle+"");
				if (!result) {
					/* Script may fail or may not be supported on certain platforms. */
					System.out.println("Script was not executed.");
				}
			}
		});
		/* Load an HTML document */
		browser.setUrl("http://www.eclipse.org");
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}


', now(), now());
insert into SNIPPET values (10067, 1, '[SWT]SnippetSnippet16.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Display example snippet: create one repeating timer (every 500 ms)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet16 {

public static void main (String [] args) {
	final Display display = new Display ();
	final Shell shell = new Shell (display);
	final int time = 500;
	Runnable timer = new Runnable () {
		public void run () {
			Point point = display.getCursorLocation ();
			Rectangle rect = shell.getBounds ();
			if (rect.contains (point)) {
				System.out.println ("In");
			} else {
				System.out.println ("Out");
			}
			display.timerExec (time, this);
		}
	};
	display.timerExec (time, timer);
	shell.setSize (200, 200);
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();	
}
} 
', now(), now());
insert into SNIPPET values (10068, 1, '[SWT]SnippetSnippet160.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Browser example snippet: query DOM node value
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet160 {
	public static void main(String [] args) {
		final String html = "<html><title>Snippet</title><body><p id=myid>Best Friends</p><p id=myid2>Cat and Dog</p></body></html>";
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		final Browser browser;
		try {
			browser = new Browser(shell, SWT.NONE);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			return;
		}
		browser.addStatusTextListener(new StatusTextListener() {
			public void changed(StatusTextEvent event) {
				browser.setData("query", event.text);
			}
		});
		browser.addProgressListener(new ProgressListener() {
			public void changed(ProgressEvent event) {
			}
			public void completed(ProgressEvent event) {
				/* 
				 * Use JavaScript to query the desired node content through the Document Object Model
				 * 
				 * Assign result to the window property status to pass the result to the StatusTextListener
				 * This trick is required since <code>execute</code> does not return the <code>String</code>
				 * directly.
				 */
				boolean result = browser.execute("window.status=document.getElementById(myid).childNodes[0].nodeValue;");
				if (!result) {
					/* Script may fail or may not be supported on certain platforms. */
					System.out.println("Script was not executed.");
					return;
				}
				String value = (String)browser.getData("query");
				System.out.println("Node value: "+value);
			}
		});
		/* Load an HTML document */
		browser.setText(html);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}


', now(), now());
insert into SNIPPET values (10069, 1, '[SWT]SnippetSnippet161.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Browser example snippet: modify DOM (executing javascript)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet161 {
	public static void main(String [] args) {
		final String html = "<html><title>Snippet</title><body><p id=myid>Best Friends</p><p id=myid2>Cat and Dog</p></body></html>";
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		final Browser browser;
		try {
			browser = new Browser(shell, SWT.BORDER);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			return;
		}
		Composite comp = new Composite(shell, SWT.NONE);
		comp.setLayout(new FillLayout(SWT.VERTICAL));
		final Text text = new Text(comp, SWT.MULTI);
		text.setText("var newNode = document.createElement(P); \r\n"+
				"var text = document.createTextNode(At least when I am around);\r\n"+
				"newNode.appendChild(text);\r\n"+
				"document.getElementById(myid).appendChild(newNode);\r\n"+
				"\r\n"+
				"document.bgColor=yellow;");
		final Button button = new Button(comp, SWT.PUSH);
		button.setText("Execute Script");
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				boolean result = browser.execute(text.getText());
				if (!result) {
					/* Script may fail or may not be supported on certain platforms. */
					System.out.println("Script was not executed.");
				}
			}
		});
		browser.setText(html);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}


', now(), now());
insert into SNIPPET values (10070, 1, '[SWT]SnippetSnippet162.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Adding an accessible listener to provide state information
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.accessibility.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet162 {

final static String STATE = "CheckedIndices";

public static void main (String [] args) {
	final Display display = new Display ();
	Image checkedImage = getCheckedImage (display);
	Image uncheckedImage = getUncheckedImage (display);
	Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	final Table table = new Table (shell, SWT.BORDER);
	TableColumn column1 = new TableColumn (table, SWT.NONE);
	TableColumn column2 = new TableColumn (table, SWT.NONE);
	TableColumn column3 = new TableColumn (table, SWT.NONE);
	TableItem item1 = new TableItem (table, SWT.NONE);
	item1.setText (new String [] {"first item", "a", "b"});
	item1.setImage (1, uncheckedImage);
	item1.setImage (2, uncheckedImage);
	item1.setData (STATE, null);
	TableItem item2 = new TableItem (table, SWT.NONE);
	item2.setText (new String [] {"second item", "c", "d"});
	item2.setImage (1, uncheckedImage);
	item2.setImage (2, checkedImage);
	item2.setData (STATE, new int [] {2});
	TableItem item3 = new TableItem (table, SWT.NONE);
	item3.setText (new String [] {"third", "e", "f"});
	item3.setImage (1, checkedImage);
	item3.setImage (2, checkedImage);
	item3.setData (STATE, new int [] {1, 2});
	column1.pack ();
	column2.pack ();
	column3.pack ();
	Accessible accessible = table.getAccessible ();
	accessible.addAccessibleListener (new AccessibleAdapter () {
		public void getName (AccessibleEvent e) {
			super.getName (e);
			if (e.childID >= 0 && e.childID < table.getItemCount ()) {
				TableItem item = table.getItem (e.childID);
				Point pt = display.getCursorLocation ();
				pt = display.map (null, table, pt);
				for (int i = 0; i < table.getColumnCount (); i++) {
					if (item.getBounds (i).contains (pt)) {
						int [] data = (int []) item.getData (STATE);
						boolean checked = false;
						if (data != null) {
							for (int j = 0; j < data.length; j++) {
								if (data [j] == i) {
									checked = true;
									break;
								}
							}
						}
						e.result = item.getText (i) + " " + (checked ? "checked" : "unchecked");
						break;
					}
				}
			}
		}
	});
	accessible.addAccessibleControlListener (new AccessibleControlAdapter () {
		public void getState (AccessibleControlEvent e) {
			super.getState (e);
			if (e.childID >= 0 && e.childID < table.getItemCount ()) {
				TableItem item = table.getItem (e.childID);
				int [] data = (int []) item.getData (STATE);
				if (data != null) {
					Point pt = display.getCursorLocation ();
					pt = display.map (null, table, pt);
					for (int i = 0; i < data.length; i++) {
						if (item.getBounds (data [i]).contains (pt)) {
							e.detail |= ACC.STATE_CHECKED;
							break;
						}
					}
				}
			}
		}
	});
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	checkedImage.dispose ();
	uncheckedImage.dispose ();
	display.dispose ();
}

static Image getCheckedImage (Display display) {
	Image image = new Image (display, 16, 16);
	GC gc = new GC (image);
	gc.setBackground (display.getSystemColor (SWT.COLOR_YELLOW));
	gc.fillOval (0, 0, 16, 16);
	gc.setForeground (display.getSystemColor (SWT.COLOR_DARK_GREEN));
	gc.drawLine (0, 0, 16, 16);
	gc.drawLine (16, 0, 0, 16);
	gc.dispose ();
	return image;
}

static Image getUncheckedImage (Display display) {
	Image image = new Image (display, 16, 16);
	GC gc = new GC (image);
	gc.setBackground (display.getSystemColor (SWT.COLOR_YELLOW));
	gc.fillOval (0, 0, 16, 16);
	gc.dispose ();
	return image;
}
}', now(), now());
insert into SNIPPET values (10071, 1, '[SWT]SnippetSnippet163.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Setting the font style, foreground and background colors of StyledText
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet163 {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	StyledText text = new StyledText (shell, SWT.BORDER);
	text.setText("0123456789 ABCDEFGHIJKLM NOPQRSTUVWXYZ");
	// make 0123456789 appear bold
	StyleRange style1 = new StyleRange();
	style1.start = 0;
	style1.length = 10;
	style1.fontStyle = SWT.BOLD;
	text.setStyleRange(style1);
	// make ABCDEFGHIJKLM have a red font
	StyleRange style2 = new StyleRange();
	style2.start = 11;
	style2.length = 13;
	style2.foreground = display.getSystemColor(SWT.COLOR_RED);
	text.setStyleRange(style2);
	// make NOPQRSTUVWXYZ have a blue background
	StyleRange style3 = new StyleRange();
	style3.start = 25;
	style3.length = 13;
	style3.background = display.getSystemColor(SWT.COLOR_BLUE);
	text.setStyleRange(style3);
	
	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}', now(), now());
insert into SNIPPET values (10072, 1, '[SWT]SnippetSnippet164.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Override the text that is spoken for a native Button.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.accessibility.*;
import org.eclipse.swt.widgets.*;

public class Snippet164 {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setBounds(10, 10, 200, 200);

	Button button1 = new Button (shell, SWT.PUSH);
	button1.setText("&Typical button");
	button1.setBounds(10,10,180,30);
	Button button2 = new Button (shell, SWT.PUSH);
	button2.setText("&Overidden button");
	button2.setBounds(10,50,180,30);
	button2.getAccessible().addAccessibleListener(new AccessibleAdapter() {
		public void getName(AccessibleEvent e) {
			e.result = "Speak this instead of the button text";
		}
	});
	
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10073, 1, '[SWT]SnippetSnippet165.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Create a CTabFolder with min and max buttons, as well as close button and 
 * image only on selected tab.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet165 {

public static void main (String [] args) {
	Display display = new Display ();
	Image image = new Image(display, 16, 16);
	GC gc = new GC(image);
	gc.setBackground(display.getSystemColor(SWT.COLOR_BLUE));
	gc.fillRectangle(0, 0, 16, 16);
	gc.setBackground(display.getSystemColor(SWT.COLOR_YELLOW));
	gc.fillRectangle(3, 3, 10, 10);
	gc.dispose();
	final Shell shell = new Shell (display);
	shell.setLayout(new GridLayout());
	final CTabFolder folder = new CTabFolder(shell, SWT.BORDER);
	folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
	folder.setSimple(false);
	folder.setUnselectedImageVisible(false);
	folder.setUnselectedCloseVisible(false);
	for (int i = 0; i < 8; i++) {
		CTabItem item = new CTabItem(folder, SWT.CLOSE);
		item.setText("Item "+i);
		item.setImage(image);
		Text text = new Text(folder, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
		text.setText("Text for item "+i+"\n\none, two, three\n\nabcdefghijklmnop");
		item.setControl(text);
	}
	folder.setMinimizeVisible(true);
	folder.setMaximizeVisible(true);
	folder.addCTabFolder2Listener(new CTabFolder2Adapter() {
		public void minimize(CTabFolderEvent event) {
			folder.setMinimized(true);
			shell.layout(true);
		}
		public void maximize(CTabFolderEvent event) {
			folder.setMaximized(true);
			folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
			shell.layout(true);
		}
		public void restore(CTabFolderEvent event) {
			folder.setMinimized(false);
			folder.setMaximized(false);
			folder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
			shell.layout(true);
		}
	});
	shell.setSize(300, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	image.dispose();
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10074, 1, '[SWT]SnippetSnippet166.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Create a ScrolledComposite with wrapping content.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet166 {

public static void main(String[] args) {
	Display display = new Display();
	Image image1 = display.getSystemImage(SWT.ICON_WORKING);
	Image image2 = display.getSystemImage(SWT.ICON_QUESTION);
	Image image3 = display.getSystemImage(SWT.ICON_ERROR);
	
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	
	final ScrolledComposite scrollComposite = new ScrolledComposite(shell, SWT.V_SCROLL | SWT.BORDER);

	final Composite parent = new Composite(scrollComposite, SWT.NONE);
	for(int i = 0; i <= 50; i++) {
		Label label = new Label(parent, SWT.NONE);
		if (i % 3 == 0) label.setImage(image1);
		if (i % 3 == 1) label.setImage(image2);
		if (i % 3 == 2) label.setImage(image3);
	}
	RowLayout layout = new RowLayout(SWT.HORIZONTAL);
	layout.wrap = true;
	parent.setLayout(layout);
	
	scrollComposite.setContent(parent);
	scrollComposite.setExpandVertical(true);
	scrollComposite.setExpandHorizontal(true);
	scrollComposite.addControlListener(new ControlAdapter() {
		public void controlResized(ControlEvent e) {
			Rectangle r = scrollComposite.getClientArea();
			scrollComposite.setMinSize(parent.computeSize(r.width, SWT.DEFAULT));
		}
	});

	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) {
			display.sleep();
		}
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10075, 1, '[SWT]SnippetSnippet167.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Create two ScrolledComposites that scroll in tandem.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet167 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());

	final ScrolledComposite sc1 = new ScrolledComposite (shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	Button button1 = new Button (sc1, SWT.PUSH);
	button1.setText ("Button 1");
	button1.setSize (400, 300);
	sc1.setContent (button1);

	final ScrolledComposite sc2 = new ScrolledComposite (shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	Button button2 = new Button(sc2, SWT.PUSH);
	button2.setText ("Button 2");
	button2.setSize (300, 400);
	sc2.setContent (button2);

	final ScrollBar vBar1 = sc1.getVerticalBar ();
	final ScrollBar vBar2 = sc2.getVerticalBar ();
	final ScrollBar hBar1 = sc1.getHorizontalBar ();
	final ScrollBar hBar2 = sc2.getHorizontalBar ();
	SelectionListener listener1 = new SelectionAdapter () {
		public void widgetSelected (SelectionEvent e) {
			int x =  hBar1.getSelection() * (hBar2.getMaximum() - hBar2.getThumb()) / Math.max(1, hBar1.getMaximum() - hBar1.getThumb());
			int y =  vBar1.getSelection() * (vBar2.getMaximum() - vBar2.getThumb()) / Math.max(1, vBar1.getMaximum() - vBar1.getThumb());
			sc2.setOrigin (x, y);
		}
	};
	SelectionListener listener2 = new SelectionAdapter () {
		public void widgetSelected (SelectionEvent e) {
			int x =  hBar2.getSelection() * (hBar1.getMaximum() - hBar1.getThumb()) / Math.max(1, hBar2.getMaximum() - hBar2.getThumb());
			int y =  vBar2.getSelection() * (vBar1.getMaximum() - vBar1.getThumb()) / Math.max(1, vBar2.getMaximum() - vBar2.getThumb());
			sc1.setOrigin (x, y);
		}
	};
	vBar1.addSelectionListener (listener1);
	hBar1.addSelectionListener (listener1);
	vBar2.addSelectionListener (listener2);
	hBar2.addSelectionListener (listener2);

	shell.setSize (400, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10076, 1, '[SWT]SnippetSnippet168.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Draw lines and polygons with different cap and join styles
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet168 {

public static void main(String[] args) {
	final Display display = new Display();
	Shell shell = new Shell(display);
	shell.addListener(SWT.Paint, new Listener() {
		public void handleEvent(Event event) {
			int x = 20, y = 20, w = 120, h = 60;
			GC gc = event.gc;
			gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
			gc.setLineWidth(10);
			int[] caps = {SWT.CAP_FLAT, SWT.CAP_ROUND, SWT.CAP_SQUARE};
			for (int i = 0; i < caps.length; i++) {
				gc.setLineCap(caps[i]);
				gc.drawLine(x, y, x + w, y);
				y += 20;
			}
			int[] joins = {SWT.JOIN_BEVEL, SWT.JOIN_MITER, SWT.JOIN_ROUND};
			for (int i = 0; i < joins.length; i++) {
				gc.setLineJoin(joins[i]);
				gc.drawPolygon(new int[] {x, y, x + w/2, y + h, x + w, y});
				y += h + 20;
			}
		}
	});
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10077, 1, '[SWT]SnippetSnippet169.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Make a toggle button have radio behavior
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet169 {
public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	Listener listener = new Listener () {
		public void handleEvent (Event e) {
			Control [] children = shell.getChildren ();
			for (int i=0; i<children.length; i++) {
				Control child = children [i];
				if (e.widget != child && child instanceof Button && (child.getStyle () & SWT.TOGGLE) != 0) {
					((Button) child).setSelection (false);
				}
			}
			((Button) e.widget).setSelection (true);
		}
	};
	for (int i=0; i<20; i++) {
		Button button = new Button (shell, SWT.TOGGLE);
		button.setText ("B" + i);
		button.addListener (SWT.Selection, listener);
		if (i == 0) button.setSelection (true);
	}
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10078, 1, '[SWT]SnippetSnippet17.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Slider example snippet: print scroll event details
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet17 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Slider slider = new Slider (shell, SWT.HORIZONTAL);
	slider.setBounds (10, 10, 200, 32);
	slider.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event event) {
			String string = "SWT.NONE";
			switch (event.detail) {
				case SWT.DRAG: string = "SWT.DRAG"; break;
				case SWT.HOME: string = "SWT.HOME"; break;
				case SWT.END: string = "SWT.END"; break;
				case SWT.ARROW_DOWN: string = "SWT.ARROW_DOWN"; break;
				case SWT.ARROW_UP: string = "SWT.ARROW_UP"; break;
				case SWT.PAGE_DOWN: string = "SWT.PAGE_DOWN"; break;
				case SWT.PAGE_UP: string = "SWT.PAGE_UP"; break;
			}
			System.out.println ("Scroll detail -> " + string);
		}
	});
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10079, 1, '[SWT]SnippetSnippet170.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tree example snippet: Create a Tree with columns
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet170 {
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		Tree tree = new Tree(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		tree.setHeaderVisible(true);
		TreeColumn column1 = new TreeColumn(tree, SWT.LEFT);
		column1.setText("Column 1");
		column1.setWidth(200);
		TreeColumn column2 = new TreeColumn(tree, SWT.CENTER);
		column2.setText("Column 2");
		column2.setWidth(200);
		TreeColumn column3 = new TreeColumn(tree, SWT.RIGHT);
		column3.setText("Column 3");
		column3.setWidth(200);
		for (int i = 0; i < 4; i++) {
			TreeItem item = new TreeItem(tree, SWT.NONE);
			item.setText(new String[] { "item " + i, "abc", "defghi" });
			for (int j = 0; j < 4; j++) {
				TreeItem subItem = new TreeItem(item, SWT.NONE);
				subItem.setText(new String[] { "subitem " + j, "jklmnop", "qrs" });
				for (int k = 0; k < 4; k++) {
					TreeItem subsubItem = new TreeItem(subItem, SWT.NONE);
					subsubItem.setText(new String[] { "subsubitem " + k, "tuv", "wxyz" });
				}
			}
		}
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}', now(), now());
insert into SNIPPET values (10080, 1, '[SWT]SnippetSnippet171.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Drag and Drop example snippet: define data transfer types that subclass each
 * other
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import java.io.*;

import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet171 {

/*
 * The data being transferred is an <bold>array of type MyType</bold> where
 * MyType is define as:
 */
static class MyType {
	String fileName;
	long fileLength;
	long lastModified;
}

static class MyTransfer extends ByteArrayTransfer {

	private static final String MYTYPENAME = "MytypeTransfer";
	private static final int MYTYPEID = registerType(MYTYPENAME);
	private static MyTransfer _instance = new MyTransfer();

	public static Transfer getInstance() {
		return _instance;
	}

	byte[] javaToByteArray(Object object) {
		MyType data = (MyType) object;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DataOutputStream writeOut = new DataOutputStream(out);
			byte[] buffer = data.fileName.getBytes();
			writeOut.writeInt(buffer.length);
			writeOut.write(buffer);
			writeOut.writeLong(data.fileLength);
			writeOut.writeLong(data.lastModified);
			buffer = out.toByteArray();
			writeOut.close();
			return buffer;
		} catch (IOException e) {
		}
		return null;
	}

	Object byteArrayToJava(byte[] bytes) {
		MyType data = new MyType();
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			DataInputStream readIn = new DataInputStream(in);
			int size = readIn.readInt();
			byte[] buffer = new byte[size];
			readIn.read(buffer);
			data.fileName = new String(buffer);
			data.fileLength = readIn.readLong();
			data.lastModified = readIn.readLong();
			readIn.close();
		} catch (IOException ex) {
			return null;
		}
		return data;
	}

	public void javaToNative(Object object, TransferData transferData) {
		if (!checkMyType(object) || !isSupportedType(transferData)) {
			DND.error(DND.ERROR_INVALID_DATA);
		}
		byte[] buffer = javaToByteArray(object);
		super.javaToNative(buffer, transferData);
	}

	public Object nativeToJava(TransferData transferData) {
		if (isSupportedType(transferData)) {
			byte[] buffer = (byte[]) super.nativeToJava(transferData);
			if (buffer == null)
				return null;
			return byteArrayToJava(buffer);
		}
		return null;
	}

	protected String[] getTypeNames() {
		return new String[] { MYTYPENAME };
	}

	protected int[] getTypeIds() {
		return new int[] { MYTYPEID };
	}

	boolean checkMyType(Object object) {
		return object != null && object instanceof MyType;
	}

	protected boolean validate(Object object) {
		return checkMyType(object);
	}
}

/*
 * The data being transferred is an <bold>array of type MyType2</bold>
 * where MyType2 is define as:
 */
static class MyType2 extends MyType {
	String version;
}

static class MyTransfer2 extends MyTransfer {

	private static final String MYTYPE2NAME = "Mytype2Transfer";
	private static final int MYTYPE2ID = registerType(MYTYPE2NAME);
	private static MyTransfer _instance = new MyTransfer2();

	public static Transfer getInstance() {
		return _instance;
	}

	protected String[] getTypeNames() {
		return new String[] { MYTYPE2NAME };
	}

	protected int[] getTypeIds() {
		return new int[] { MYTYPE2ID };
	}

	byte[] javaToByteArray(Object object) {
		MyType2 data = (MyType2) object;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			DataOutputStream writeOut = new DataOutputStream(out);
			byte[] buffer = data.fileName.getBytes();
			writeOut.writeInt(buffer.length);
			writeOut.write(buffer);
			writeOut.writeLong(data.fileLength);
			writeOut.writeLong(data.lastModified);
			buffer = data.version.getBytes();
			writeOut.writeInt(buffer.length);
			writeOut.write(buffer);
			buffer = out.toByteArray();
			writeOut.close();
			return buffer;
		} catch (IOException e) {
		}
		return null;
	}

	Object byteArrayToJava(byte[] bytes) {
		MyType2 data = new MyType2();
		try {
			ByteArrayInputStream in = new ByteArrayInputStream(bytes);
			DataInputStream readIn = new DataInputStream(in);
			int size = readIn.readInt();
			byte[] buffer = new byte[size];
			readIn.read(buffer);
			data.fileName = new String(buffer);
			data.fileLength = readIn.readLong();
			data.lastModified = readIn.readLong();
			size = readIn.readInt();
			buffer = new byte[size];
			readIn.read(buffer);
			data.version = new String(buffer);
			readIn.close();
		} catch (IOException ex) {
			return null;
		}
		return data;
	}

	public void javaToNative(Object object, TransferData transferData) {
		if (!checkMyType2(object)) {
			DND.error(DND.ERROR_INVALID_DATA);
		}
		super.javaToNative(object, transferData);
	}

	boolean checkMyType2(Object object) {
		if (!checkMyType(object))
			return false;
		return object != null && object instanceof MyType2;
	}

	protected boolean validate(Object object) {
		return checkMyType2(object);
	}
}

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	final Label label1 = new Label(shell, SWT.BORDER | SWT.WRAP);
	label1.setText("Drag Source for MyData and MyData2");
	final Label label2 = new Label(shell, SWT.BORDER | SWT.WRAP);
	label2.setText("Drop Target for MyData");
	final Label label3 = new Label(shell, SWT.BORDER | SWT.WRAP);
	label3.setText("Drop Target for MyData2");

	DragSource source = new DragSource(label1, DND.DROP_COPY);
	source.setTransfer(new Transfer[] { MyTransfer.getInstance(),
			MyTransfer2.getInstance() });
	source.addDragListener(new DragSourceAdapter() {
		public void dragSetData(DragSourceEvent event) {
			MyType2 myType = new MyType2();
			myType.fileName = "C:\\abc.txt";
			myType.fileLength = 1000;
			myType.lastModified = 12312313;
			myType.version = "version 2";
			event.data = myType;
		}
	});
	DropTarget targetMyType = new DropTarget(label2, DND.DROP_COPY | DND.DROP_DEFAULT);
	targetMyType.setTransfer(new Transfer[] { MyTransfer.getInstance() });
	targetMyType.addDropListener(new DropTargetAdapter() {
		public void dragEnter(DropTargetEvent event) {
			if (event.detail == DND.DROP_DEFAULT)
				event.detail = DND.DROP_COPY;
		}

		public void dragOperationChanged(DropTargetEvent event) {
			if (event.detail == DND.DROP_DEFAULT)
				event.detail = DND.DROP_COPY;
		}

		public void drop(DropTargetEvent event) {
			if (event.data != null) {
				MyType myType = (MyType) event.data;
				if (myType != null) {
					String string = "MyType: " + myType.fileName;
					label2.setText(string);
				}
			}
		}

	});
	DropTarget targetMyType2 = new DropTarget(label3, DND.DROP_COPY	| DND.DROP_DEFAULT);
	targetMyType2.setTransfer(new Transfer[] { MyTransfer2.getInstance() });
	targetMyType2.addDropListener(new DropTargetAdapter() {
		public void dragEnter(DropTargetEvent event) {
			if (event.detail == DND.DROP_DEFAULT)
				event.detail = DND.DROP_COPY;
		}

		public void dragOperationChanged(DropTargetEvent event) {
			if (event.detail == DND.DROP_DEFAULT)
				event.detail = DND.DROP_COPY;
		}

		public void drop(DropTargetEvent event) {
			if (event.data != null) {
				MyType2 myType = (MyType2) event.data;
				if (myType != null) {
					String string = "MyType2: " + myType.fileName + ":"
							+ myType.version;
					label3.setText(string);
				}
			}
		}

	});
	shell.setSize(300, 200);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}', now(), now());
insert into SNIPPET values (10081, 1, '[SWT]SnippetSnippet172.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * GridLayout snippet: align widgets in a GridLayout
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet172 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	GridLayout layout = new GridLayout(4, false);
	shell.setLayout(layout);
	
	Button b = new Button(shell, SWT.PUSH);
	b.setText("LEFT, TOP");
	b.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("LEFT, CENTER");
	b.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("LEFT, BOTTOM");
	b.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("LEFT, FILL");
	b.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("CENTER, TOP");
	b.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("CENTER, CENTER");
	b.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("CENTER, BOTTOM");
	b.setLayoutData(new GridData(SWT.CENTER, SWT.BOTTOM, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("CENTER, FILL");
	b.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("RIGHT, TOP");
	b.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("RIGHT, CENTER");
	b.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("RIGHT, BOTTOM");
	b.setLayoutData(new GridData(SWT.RIGHT, SWT.BOTTOM, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("RIGHT, FILL");
	b.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("FILL, TOP");
	b.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("FILL, CENTER");
	b.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("FILL, BOTTOM");
	b.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, true, 1, 1));
	b = new Button(shell, SWT.PUSH);
	b.setText("FILL, FILL");
	b.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10082, 1, '[SWT]SnippetSnippet173.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Browser snippet: bring up a browser with pop-up blocker
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.browser.*;

public class Snippet173 {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setText("Main Window");
	shell.setLayout(new FillLayout());
	final Browser browser;
	try {
		browser = new Browser(shell, SWT.NONE);
	} catch (SWTError e) {
		System.out.println("Could not instantiate Browser: " + e.getMessage());
		return;
	}
	initialize(display, browser);
	shell.open();
	/* any website with popups */
	browser.setUrl("http://www.cnn.com");
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
		}
		display.dispose();
	}

/* register WindowEvent listeners */
static void initialize(final Display display, Browser browser) {
	browser.addOpenWindowListener(new OpenWindowListener() {
		public void open(WindowEvent event) {
			Shell shell = new Shell(display);
			shell.setText("New Window");
			shell.setLayout(new FillLayout());
			Browser browser = new Browser(shell, SWT.NONE);
			initialize(display, browser);
			event.browser = browser;
		}
	});
	browser.addVisibilityWindowListener(new VisibilityWindowListener() {
		public void hide(WindowEvent event) {
			Browser browser = (Browser)event.widget;
			Shell shell = browser.getShell();
			shell.setVisible(false);
		}
		public void show(WindowEvent event) {
			Browser browser = (Browser)event.widget;
			final Shell shell = browser.getShell();
			/* popup blocker - ignore windows with no style */
			if (!event.addressBar && !event.menuBar && !event.statusBar && !event.toolBar) {
				System.out.println("Popup blocked.");
				event.display.asyncExec(new Runnable() {
					public void run() {
						shell.close();
					}
				});
				return;
			}
			if (event.location != null) shell.setLocation(event.location);
			if (event.size != null) {
				Point size = event.size;
				shell.setSize(shell.computeSize(size.x, size.y));
			}
			shell.open();
		}
	});
	browser.addCloseWindowListener(new CloseWindowListener() {
		public void close(WindowEvent event) {
			Browser browser = (Browser)event.widget;
			Shell shell = browser.getShell();
			shell.close();
		}
	});
}
}', now(), now());
insert into SNIPPET values (10083, 1, '[SWT]SnippetSnippet174.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Sebastian Davids - initial implementation
 *     IBM Corporation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * SWT OpenGL snippet: draw a square
 * 
 * This snippet requires the experimental org.eclipse.swt.opengl plugin, which
 * is not included in SWT by default and should only be used with versions of
 * SWT prior to 3.2.  For information on using OpenGL in SWT see
 * http://www.eclipse.org/swt/opengl/ .
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
import org.eclipse.opengl.*;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.opengl.*;
import org.eclipse.swt.widgets.*;

public class Snippet174 {

public static void main(String[] args) {
    Display display = new Display();
    Shell shell = new Shell(display);
    shell.setText("OpenGL in SWT");
    shell.setLayout(new FillLayout());
    GLData data = new GLData();
    data.doubleBuffer = true;
    final GLCanvas canvas = new GLCanvas(shell, SWT.NO_BACKGROUND, data);
    canvas.addControlListener(new ControlAdapter() {
        public void controlResized(ControlEvent e) {
            resize(canvas);
        }
    });
    init(canvas);
    new Runnable() {
        public void run() {
            if (canvas.isDisposed()) return;
            render();
            canvas.swapBuffers();
            canvas.getDisplay().timerExec(50, this);
        }
    }.run();
    shell.open();
    while (!shell.isDisposed()) {
        if (!display.readAndDispatch()) display.sleep();
    }
    display.dispose();
}

static void init(GLCanvas canvas) {
    canvas.setCurrent();
    resize(canvas);
    GL.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
    GL.glColor3f(0.0f, 0.0f, 0.0f);
    GL.glClearDepth(1.0f);
    GL.glEnable(GL.GL_DEPTH_TEST);
    GL.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
}

static void render() {
    GL.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
    GL.glLoadIdentity();
    GL.glTranslatef(0.0f, 0.0f, -6.0f);
    GL.glBegin(GL.GL_QUADS);
    GL.glVertex3f(-1.0f, 1.0f, 0.0f);
    GL.glVertex3f(1.0f, 1.0f, 0.0f);
    GL.glVertex3f(1.0f, -1.0f, 0.0f);
    GL.glVertex3f(-1.0f, -1.0f, 0.0f);
    GL.glEnd();
}

static void resize(GLCanvas canvas) {
    canvas.setCurrent();
    Rectangle rect = canvas.getClientArea();
    int width = rect.width;
    int height = Math.max(rect.height, 1);
    GL.glViewport(0, 0, width, height);
    GL.glMatrixMode(GL.GL_PROJECTION);
    GL.glLoadIdentity();
    float aspect = (float) width / (float) height;
    GLU.gluPerspective(45.0f, aspect, 0.5f, 400.0f);
    GL.glMatrixMode(GL.GL_MODELVIEW);
    GL.glLoadIdentity();
}
}
', now(), now());
insert into SNIPPET values (10084, 1, '[SWT]SnippetSnippet175.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/*
 * Exclude a widget from a GridLayout
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */

public class Snippet175 {

public static void main(String[] args) {

	Display display = new Display();
	final Shell shell = new Shell(display);
	shell.setLayout(new GridLayout(3, false));

	Button b = new Button(shell, SWT.PUSH);
	b.setText("Button 0");

	final Button bHidden = new Button(shell, SWT.PUSH);
	bHidden.setText("Button 1");
	GridData data = new GridData();
	data.exclude = true;
	data.horizontalSpan = 2;
	data.horizontalAlignment = SWT.FILL;
	bHidden.setLayoutData(data);

	b = new Button(shell, SWT.PUSH);
	b.setText("Button 2");
	b = new Button(shell, SWT.PUSH);
	b.setText("Button 3");
	b = new Button(shell, SWT.PUSH);
	b.setText("Button 4");

	b = new Button(shell, SWT.CHECK);
	b.setText("hide");
	b.setSelection(true);
	b.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event e) {
			Button b = (Button) e.widget;
			GridData data = (GridData) bHidden.getLayoutData();
			data.exclude = b.getSelection();
			bHidden.setVisible(!data.exclude);
			shell.layout(false);
		}
	});
	shell.setSize(400, 400);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10085, 1, '[SWT]SnippetSnippet176.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * RowLayout snippet: align widgets in a row
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet176 {

	public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	RowLayout layout = new RowLayout(SWT.HORIZONTAL);
	layout.wrap = true;
	layout.fill = false;
	layout.justify = true;
	shell.setLayout(layout);

	Button b = new Button(shell, SWT.PUSH);
	b.setText("Button 1");
	b = new Button(shell, SWT.PUSH);

	b.setText("Button 2");

	b = new Button(shell, SWT.PUSH);
	b.setText("Button 3");

	b = new Button(shell, SWT.PUSH);
	b.setText("Not shown");
	b.setVisible(false);
	RowData data = new RowData();
	data.exclude = true;
	b.setLayoutData(data);

	b = new Button(shell, SWT.PUSH);
	b.setText("Button 200 high");
	data = new RowData();
	data.height = 200;
	b.setLayoutData(data);

	b = new Button(shell, SWT.PUSH);
	b.setText("Button 200 wide");
	data = new RowData();
	data.width = 200;
	b.setLayoutData(data);

	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10086, 1, '[SWT]SnippetSnippet177.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * RowLayout snippet: align widgets in a column
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet177 {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	RowLayout layout = new RowLayout(SWT.VERTICAL);
	layout.wrap = true;
	layout.fill = true;
	layout.justify = false;
	shell.setLayout(layout);

	Button b = new Button(shell, SWT.PUSH);
	b.setText("Button 1");
	b = new Button(shell, SWT.PUSH);

	b.setText("Button 2");

	b = new Button(shell, SWT.PUSH);
	b.setText("Button 3");

	b = new Button(shell, SWT.PUSH);
	b.setText("Not shown");
	b.setVisible(false);
	RowData data = new RowData();
	data.exclude = true;
	b.setLayoutData(data);

	b = new Button(shell, SWT.PUSH);
	b.setText("Button 200 high");
	data = new RowData();
	data.height = 200;
	b.setLayoutData(data);

	b = new Button(shell, SWT.PUSH);
	b.setText("Button 200 wide");
	data = new RowData();
	data.width = 200;
	b.setLayoutData(data);

	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10087, 1, '[SWT]SnippetSnippet178.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * How to access About, Preferences and Quit menus on carbon.
 * NOTE: This snippet uses internal SWT packages that are
 * subject to change without notice.
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import org.eclipse.swt.internal.*;
import org.eclipse.swt.internal.carbon.*;

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet178 {

	private static final int kHICommandPreferences = (p << 24) + (r << 16)
			+ (e << 8) + f;

	private static final int kHICommandAbout = (a << 24) + (b << 16)
			+ (o << 8) + u;

	private static final int kHICommandServices = (s << 24) + (e << 16)
			+ (r << 8) + v;

public static void main(String[] arg) {
	Display.setAppName("AppMenu"); // sets name in Dock
	Display display = new Display();
	hookApplicationMenu(display, "About AppMenu");
	Shell shell = new Shell(display);
	shell.setText("Main Window");
	shell.open();
	while (!shell.isDisposed())
		if (!display.readAndDispatch())
			display.sleep();

	display.dispose();
}

static void hookApplicationMenu(Display display, final String aboutName) {
	// Callback target
	Object target = new Object() {
		int commandProc(int nextHandler, int theEvent, int userData) {
			if (OS.GetEventKind(theEvent) == OS.kEventProcessCommand) {
				HICommand command = new HICommand();
				OS.GetEventParameter(theEvent, OS.kEventParamDirectObject,
						OS.typeHICommand, null, HICommand.sizeof, null,
						command);
				switch (command.commandID) {
				case kHICommandPreferences:
					return handleCommand("Preferences"); //$NON-NLS-1$
				case kHICommandAbout:
					return handleCommand(aboutName);
				default:
					break;
				}
			}
			return OS.eventNotHandledErr;
		}

		int handleCommand(String command) {
			Shell shell = new Shell();
			MessageBox preferences = new MessageBox(shell, SWT.ICON_WARNING);
			preferences.setText(command);
			preferences.open();
			shell.dispose();
			return OS.noErr;
		}
	};

	final Callback commandCallback = new Callback(target, "commandProc", 3); //$NON-NLS-1$
	int commandProc = commandCallback.getAddress();
	if (commandProc == 0) {
		commandCallback.dispose();
		return; // give up
	}

	// Install event handler for commands
	int[] mask = new int[] { OS.kEventClassCommand, OS.kEventProcessCommand };
	OS.InstallEventHandler(OS.GetApplicationEventTarget(), commandProc,
			mask.length / 2, mask, 0, null);

	// create About ... menu command
	int[] outMenu = new int[1];
	short[] outIndex = new short[1];
	if (OS.GetIndMenuItemWithCommandID(0, kHICommandPreferences, 1,
			outMenu, outIndex) == OS.noErr
			&& outMenu[0] != 0) {
		int menu = outMenu[0];

		int l = aboutName.length();
		char buffer[] = new char[l];
		aboutName.getChars(0, l, buffer, 0);
		int str = OS.CFStringCreateWithCharacters(OS.kCFAllocatorDefault,
				buffer, l);
		OS.InsertMenuItemTextWithCFString(menu, str, (short) 0, 0,
				kHICommandAbout);
		OS.CFRelease(str);

		// add separator between About & Preferences
		OS.InsertMenuItemTextWithCFString(menu, 0, (short) 1,
				OS.kMenuItemAttrSeparator, 0);

		// enable pref menu
		OS.EnableMenuCommand(menu, kHICommandPreferences);

		// disable services menu
		OS.DisableMenuCommand(menu, kHICommandServices);
	}

	// schedule disposal of callback object
	display.disposeExec(new Runnable() {
		public void run() {
			commandCallback.dispose();
		}
	});
}
}
', now(), now());
insert into SNIPPET values (10088, 1, '[SWT]SnippetSnippet179.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Text example snippet: verify input (format for date)
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import java.util.*;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet179 {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new GridLayout());
	final Text text = new Text(shell, SWT.BORDER);
	text.setText("YYYY/MM/DD");;	
	final Calendar calendar = Calendar.getInstance();
	text.addListener(SWT.Verify, new Listener() {
		boolean ignore;
		public void handleEvent(Event e) {
			if (ignore) return;
			e.doit = false;
			StringBuffer buffer = new StringBuffer(e.text);
			char[] chars = new char[buffer.length()];
			buffer.getChars(0, chars.length, chars, 0);
			if (e.character == \b) {
				for (int i = e.start; i < e.end; i++) {
					switch (i) {
						case 0: /* [Y]YYY */
						case 1: /* Y[Y]YY */
						case 2: /* YY[Y]Y */
						case 3: /* YYY[Y] */ {
							buffer.append(Y); 	break;
						}
						case 5: /* [M]M*/
						case 6: /* M[M] */{
							buffer.append(M); break;
						}
						case 8: /* [D]D */
						case 9: /* D[D] */ {
							buffer.append(D); break;
						}
						case 4: /* YYYY[/]MM */
						case 7: /* MM[/]DD */ {
							buffer.append(/); break;
						}
						default:
							return;
					}
				}
				text.setSelection(e.start, e.start + buffer.length());
				ignore = true;
				text.insert(buffer.toString());
				ignore = false;
				text.setSelection(e.start, e.start);
				return;
			}
		
			int start = e.start;
			if (start > 9) return;
			int index = 0;
			for (int i = 0; i < chars.length; i++) {
				if (start + index == 4 || start + index == 7) {
					if (chars[i] == /) {
						index++;
						continue;
					}
					buffer.insert(index++, /);
				}
				if (chars[i] < 0 || 9 < chars[i]) return;
				if (start + index == 5 &&  1 < chars[i]) return; /* [M]M */
				if (start + index == 8 &&  3 < chars[i]) return; /* [D]D */
				index++;
			}
			String newText = buffer.toString();
			int length = newText.length();
			StringBuffer date = new StringBuffer(text.getText());
			date.replace(e.start, e.start + length, newText);
			calendar.set(Calendar.YEAR, 1901);
			calendar.set(Calendar.MONTH, Calendar.JANUARY);
			calendar.set(Calendar.DATE, 1);
			String yyyy = date.substring(0, 4);
			if (yyyy.indexOf(Y) == -1) {
				int year = Integer.parseInt(yyyy);
				calendar.set(Calendar.YEAR, year);
			}
			String mm = date.substring(5, 7);
			if (mm.indexOf(M) == -1) {
				int month =  Integer.parseInt(mm) - 1;
				int maxMonth = calendar.getActualMaximum(Calendar.MONTH);
				if (0 > month || month > maxMonth) return;
				calendar.set(Calendar.MONTH, month);
			}
			String dd = date.substring(8,10);
			if (dd.indexOf(D) == -1) {
				int day = Integer.parseInt(dd);
				int maxDay = calendar.getActualMaximum(Calendar.DATE);
				if (1 > day || day > maxDay) return;
				calendar.set(Calendar.DATE, day);
			} else {
				if (calendar.get(Calendar.MONTH)  == Calendar.FEBRUARY) {
					char firstChar = date.charAt(8);
					if (firstChar != D && 2 < firstChar) return;
				}
			}
			text.setSelection(e.start, e.start + length);
			ignore = true;
			text.insert(newText);
			ignore = false;
		}
	});
	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10089, 1, '[SWT]SnippetSnippet18.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * ToolBar example snippet: create a tool bar (text)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet18 {

public static void main (String [] args) {
	Shell shell = new Shell ();
	ToolBar bar = new ToolBar (shell, SWT.BORDER);
	for (int i=0; i<8; i++) {
		ToolItem item = new ToolItem (bar, SWT.PUSH);
		item.setText ("Item " + i);
	}
	bar.pack ();
	shell.open ();
	Display display = shell.getDisplay ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
} 
}
', now(), now());
insert into SNIPPET values (10090, 1, '[SWT]SnippetSnippet180.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Create a non-rectangular shell to simulate transparency
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet180 {

public static void main(String[] args) {
	Display display = new Display();
	final Image image = display.getSystemImage(SWT.ICON_WARNING);
	//Shell must be created with style SWT.NO_TRIM
	final Shell shell = new Shell(display, SWT.NO_TRIM | SWT.ON_TOP);
	shell.setBackground(display.getSystemColor(SWT.COLOR_RED));
	//define a region 
	Region region = new Region();
	Rectangle pixel = new Rectangle(0, 0, 1, 1);
	for (int y = 0; y < 200; y+=2) {
			for (int x = 0; x < 200; x+=2) {
				pixel.x = x;
				pixel.y = y;
				region.add(pixel);
			}
		}
	//define the shape of the shell using setRegion
	shell.setRegion(region);
	Rectangle size = region.getBounds();
	shell.setSize(size.width, size.height);
	shell.addPaintListener(new PaintListener() {
		public void paintControl(PaintEvent e) {
			Rectangle bounds = image.getBounds();
			Point size = shell.getSize();
			e.gc.drawImage(image, 0, 0, bounds.width, bounds.height, 10, 10, size.x-20, size.y-20);
		}
	});
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	region.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10091, 1, '[SWT]SnippetSnippet181.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Allow user to reorder columns and reorder columns programmatically.
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet181 {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new RowLayout(SWT.HORIZONTAL));
		final Table table = new Table(shell, SWT.BORDER | SWT.CHECK);
		table.setLayoutData(new RowData(-1, 300));
		table.setHeaderVisible(true);
		TableColumn column = new TableColumn(table, SWT.LEFT);
		column.setText("Column 0");
		column = new TableColumn(table, SWT.CENTER);
		column.setText("Column 1");
		column = new TableColumn(table, SWT.CENTER);
		column.setText("Column 2");
		column = new TableColumn(table, SWT.CENTER);
		column.setText("Column 3");
		column = new TableColumn(table, SWT.CENTER);
		column.setText("Column 4");
		for (int i = 0; i < 100; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			String[] text = new String[]{i+" 0", i+" 1", i+" 2", i+" 3", i+" 4"};
			item.setText(text);
		}
		Listener listener = new Listener() {
			public void handleEvent(Event e) {
				System.out.println("Move "+e.widget);
			}
		};
		TableColumn[] columns = table.getColumns();
		for (int i = 0; i < columns.length; i++) {
			columns[i].pack();
			columns[i].setMoveable(true);
			columns[i].addListener(SWT.Move, listener);
		}
		Button b = new Button(shell, SWT.PUSH);
		b.setText("invert column order");
		b.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				int[] order = table.getColumnOrder();
				for (int i = 0; i < order.length / 2; i++) {
					int temp = order[i];
					order[i] = order[order.length - i - 1];
					order[order.length - i - 1] = temp;
				}
				table.setColumnOrder(order);
			}
		});
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10092, 1, '[SWT]SnippetSnippet182.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Link example snippet: create a link widget
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet182 {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		Link link = new Link(shell, SWT.BORDER);
		link.setText("This a very simple <A>link</A> widget.");
		link.setSize(140, 40);
		shell.pack ();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}', now(), now());
insert into SNIPPET values (10093, 1, '[SWT]SnippetSnippet183.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Link example snippet: detect selection events in a link widget
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.*;

public class Snippet183 {
	
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		Link link = new Link(shell, SWT.NONE);
		String text = "The SWT component is designed to provide <a>efficient</a>, <a>portable</a> <a href=\"native\">access to the user-interface facilities of the operating systems</a> on which it is implemented.";
		link.setText(text);
		link.setSize(400, 400);
		link.addListener (SWT.Selection, new Listener () {
			public void handleEvent(Event event) {
				System.out.println("Selection: " + event.text);
			}
		});
		shell.pack ();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}', now(), now());
insert into SNIPPET values (10094, 1, '[SWT]SnippetSnippet184.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Spinner example snippet: create and initialize a spinner widget
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet184 {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		Spinner spinner = new Spinner (shell, SWT.BORDER);
		spinner.setMinimum(0);
		spinner.setMaximum(1000);
		spinner.setSelection(500);
		spinner.setIncrement(1);
		spinner.setPageIncrement(100);
		spinner.pack();
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}', now(), now());
insert into SNIPPET values (10095, 1, '[SWT]SnippetSnippet185.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Make a dropped data type depend on a target item in table
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import java.io.*;

import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet185 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	Label label1 = new Label(shell, SWT.BORDER);
	label1.setText("Drag Source");
	final Table table = new Table(shell, SWT.BORDER);
	for (int i = 0; i < 4; i++) {
		TableItem item = new TableItem(table, SWT.NONE);
		if (i % 2 == 0) item.setText("Drop a file");
		if (i % 2 == 1) item.setText("Drop text");
	}
	DragSource dragSource = new DragSource(label1, DND.DROP_COPY);
	dragSource.setTransfer(new Transfer[] {TextTransfer.getInstance(), FileTransfer.getInstance()});
	dragSource.addDragListener(new DragSourceAdapter() {
		public void dragSetData(DragSourceEvent event) {
			if (FileTransfer.getInstance().isSupportedType(event.dataType)) {
				File file = new File("temp");
				event.data = new String[] {file.getAbsolutePath()};
			}
			if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
				event.data = "once upon a time";
			}
		} 
	});
	DropTarget dropTarget = new DropTarget(table, DND.DROP_COPY | DND.DROP_DEFAULT);
	dropTarget.setTransfer(new Transfer[] {TextTransfer.getInstance(), FileTransfer.getInstance()});
	dropTarget.addDropListener(new DropTargetAdapter() {
		FileTransfer fileTransfer = FileTransfer.getInstance();
		TextTransfer textTransfer = TextTransfer.getInstance();
		public void dragEnter(DropTargetEvent event) {
			if (event.detail == DND.DROP_DEFAULT) event.detail = DND.DROP_COPY;
		}
		public void dragOperationChanged(DropTargetEvent event) {
			if (event.detail == DND.DROP_DEFAULT) event.detail = DND.DROP_COPY;
		}
		public void dragOver(DropTargetEvent event) {
			event.detail = DND.DROP_NONE;
			TableItem item = (TableItem)event.item;
			if (item == null) return;
			int itemIndex = table.indexOf(item);
			if (itemIndex % 2 == 0) {
				int index = 0;
				while (index < event.dataTypes.length) {
					if (fileTransfer.isSupportedType(event.dataTypes[index])) break;
					index++;
				}
				if (index < event.dataTypes.length) {
					event.currentDataType = event.dataTypes[index];
					event.detail = DND.DROP_COPY;
					return;
				}
			} else {
				int index = 0;
				while (index < event.dataTypes.length) {
					if (textTransfer.isSupportedType(event.dataTypes[index])) break;
					index++;
				}
				if (index < event.dataTypes.length) {
					event.currentDataType = event.dataTypes[index];
					event.detail = DND.DROP_COPY;
					return;
				}
			}
		}

		public void drop(DropTargetEvent event) {
			TableItem item = (TableItem)event.item;
			if (item == null) {
				event.detail = DND.DROP_NONE;
				return;
			}
			if (fileTransfer.isSupportedType(event.currentDataType)) {
				String[] files = (String[])event.data;
				if (files != null && files.length > 0) {
					item.setText(files[0]);
				}
			}
			if (textTransfer.isSupportedType(event.currentDataType)) {
				String text = (String)event.data;
				if (text != null) {
					item.setText(text);
				}
			}
		}
		
	});
	shell.setSize(300, 150);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10096, 1, '[SWT]SnippetSnippet186.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Reading and writing to a SAFEARRAY
 * 
 * This example reads from a PostData object in a BeforeNavigate2 event and
 * creates a PostData object in a call to Navigate (32-bit win32 only).
 * NOTE: This snippet uses internal SWT packages that are
 * subject to change without notice.
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.internal.win32.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.widgets.*;

public class Snippet186 {

static int CodePage = OS.GetACP();

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new GridLayout(2, false));
	
	final Text text = new Text(shell, SWT.BORDER);
	text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
	Button go = new Button(shell, SWT.PUSH);
	go.setText("Go");
	OleFrame oleFrame = new OleFrame(shell, SWT.NONE);
	oleFrame.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
	OleControlSite controlSite;
	OleAutomation automation;
	try {
		controlSite = new OleControlSite(oleFrame, SWT.NONE, "Shell.Explorer");
		automation = new OleAutomation(controlSite);
		controlSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
	} catch (SWTException ex) {
		return;
	}
	
	final OleAutomation auto = automation;
	go.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event e) {
			String url = text.getText();
			int[] rgdispid = auto.getIDsOfNames(new String[]{"Navigate", "URL"}); 
			int dispIdMember = rgdispid[0];
			Variant[] rgvarg = new Variant[1];
			rgvarg[0] = new Variant(url);
			int[] rgdispidNamedArgs = new int[1];
			rgdispidNamedArgs[0] = rgdispid[1];
			auto.invoke(dispIdMember, rgvarg, rgdispidNamedArgs);
		}
	});
	
	
	// Read PostData whenever we navigate to a site that uses it
	int BeforeNavigate2 = 0xfa;
	controlSite.addEventListener(BeforeNavigate2, new OleListener() {
		public void handleEvent(OleEvent event) {
			Variant url = event.arguments[1];
			Variant postData = event.arguments[4];
			if (postData != null) {
				System.out.println("PostData = "+readSafeArray(postData)+", URL = "+url.getString());
			}
		}
	});
	
	// Navigate to this web site which uses post data to fill in the text field
	// and put the string "hello world" into the text box
	text.setText("file://"+Snippet186.class.getResource("Snippet186.html").getFile());
	int[] rgdispid = automation.getIDsOfNames(new String[]{"Navigate", "URL", "PostData"}); 
	int dispIdMember = rgdispid[0];	
	Variant[] rgvarg = new Variant[2];
	rgvarg[0] = new Variant(text.getText());
	rgvarg[1] = writeSafeArray("hello world");
	int[] rgdispidNamedArgs = new int[2];
	rgdispidNamedArgs[0] = rgdispid[1];
	rgdispidNamedArgs[1] = rgdispid[2];
	automation.invoke(dispIdMember, rgvarg, rgdispidNamedArgs);
		
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

// The following structs are accessed in the readSafeArray and writeSafeArray
// functions:
//
// VARIANT:
// 		short vt
// 		short wReserved1
// 		short wReserved2
// 		short wReserved3
// 		int parray
//
// SAFEARRAY:
//      short cDims      // Count of dimensions in this array
//      short fFeatures  // Flags used by the SafeArray
//      int cbElements   // Size of an element of the array
//      int cLocks       // Number of times the array has been locked without corresponding unlock
//      int pvData       // Pointer to the data
//      SAFEARRAYBOUND[] rgsabound // One bound for each dimension
//
// SAFEARRAYBOUND:
//      int cElements    // the number of elements in the dimension
//      int lLbound      // the lower bound of the dimension 

static String readSafeArray(Variant variantByRef) {
	// Read a safearray that contains data of 
	// type VT_UI1 (unsigned shorts) which contains
	// a text stream.
    int pPostData = variantByRef.getByRef();
    short[] vt_type = new short[1];
    OS.MoveMemory(vt_type, pPostData, 2);
    String result = null;
    if (vt_type[0] == (short)(OLE.VT_BYREF | OLE.VT_VARIANT)) {
        int[] pVariant = new int[1];
        OS.MoveMemory(pVariant, pPostData + 8, 4);
        vt_type = new short[1];
        OS.MoveMemory(vt_type, pVariant[0], 2);
        if (vt_type[0] == (short)(OLE.VT_ARRAY | OLE.VT_UI1)) {
            int[] pSafearray = new int[1];
            OS.MoveMemory(pSafearray, pVariant[0] + 8, 4);
            short[] cDims = new short[1];
            OS.MoveMemory(cDims, pSafearray[0], 2);
            int[] pvData = new int[1];
            OS.MoveMemory(pvData, pSafearray[0] + 12, 4);
            int safearrayboundOffset = 0;
            for (int i = 0; i < cDims[0]; i++) {
                int[] cElements = new int[1];
                OS.MoveMemory(cElements, pSafearray[0] + 16 + safearrayboundOffset, 4);
                safearrayboundOffset += 8;
                int cchWideChar = OS.MultiByteToWideChar (CodePage, OS.MB_PRECOMPOSED,  pvData[0], -1, null, 0);
				if (cchWideChar == 0) return null;
				char[] lpWideCharStr = new char [cchWideChar - 1];
				OS.MultiByteToWideChar (CodePage, OS.MB_PRECOMPOSED,  pvData[0], -1, lpWideCharStr, lpWideCharStr.length);
				result = new String(lpWideCharStr);
            }
        }
    }
    return result;
}

static Variant writeSafeArray (String string) {
	// Create a one dimensional safearray containing two VT_UI1 values
	// where VT_UI1 is an unsigned char
	
	// Define cDims, fFeatures and cbElements
	short cDims = 1;
	short FADF_FIXEDSIZE = 0x10;
	short FADF_HAVEVARTYPE = 0x80;
	short fFeatures = (short)(FADF_FIXEDSIZE | FADF_HAVEVARTYPE);
	int cbElements = 1;
	// Create a pointer and copy the data into it
	int count = string.length();
	char[] chars = new char[count + 1];
	string.getChars(0, count, chars, 0);
	int cchMultiByte = OS.WideCharToMultiByte(CodePage, 0, chars, -1, null, 0, null, null);
	if (cchMultiByte == 0) return null;
	int pvData = OS.GlobalAlloc(OS.GMEM_FIXED | OS.GMEM_ZEROINIT, cchMultiByte);
	OS.WideCharToMultiByte(CodePage, 0, chars, -1, pvData, cchMultiByte, null, null);
	int cElements1 = cchMultiByte;
	int lLbound1 = 0;
	// Create a safearray in memory
	// 12 bytes for cDims, fFeatures and cbElements + 4 bytes for pvData + number of dimensions * (size of safearraybound)
	int sizeofSafeArray = 12 + 4 + 1*8;
	int pSafeArray = OS.GlobalAlloc(OS.GMEM_FIXED | OS.GMEM_ZEROINIT, sizeofSafeArray);
	// Copy the data into the safe array
	int offset = 0;
	OS.MoveMemory(pSafeArray + offset, new short[] {cDims}, 2); offset += 2;
	OS.MoveMemory(pSafeArray + offset, new short[] {fFeatures}, 2); offset += 2;
	OS.MoveMemory(pSafeArray + offset, new int[] {cbElements}, 4); offset += 4;
	OS.MoveMemory(pSafeArray + offset, new int[] {0}, 4); offset += 4;
	OS.MoveMemory(pSafeArray + offset, new int[] {pvData}, 4); offset += 4;
	OS.MoveMemory(pSafeArray + offset, new int[] {cElements1}, 4); offset += 4;
	OS.MoveMemory(pSafeArray + offset, new int[] {lLbound1}, 4); offset += 4;
	// Create a variant in memory to hold the safearray
	int pVariant = OS.GlobalAlloc(OS.GMEM_FIXED | OS.GMEM_ZEROINIT, Variant.sizeof);
	short vt = (short)(OLE.VT_ARRAY | OLE.VT_UI1);
	OS.MoveMemory(pVariant, new short[] {vt}, 2);
	OS.MoveMemory(pVariant + 8, new int[]{pSafeArray}, 4);
	// Create a by ref variant
	Variant variantByRef = new Variant(pVariant, (short)(OLE.VT_BYREF | OLE.VT_VARIANT));
	return variantByRef;
}
}
', now(), now());
insert into SNIPPET values (10097, 1, '[SWT]SnippetSnippet187.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.widgets.*;

/*
 * Running a script within IE. (win32 only)
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

public class Snippet187 {
	
public static void main(String[] args) {
	final Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	OleControlSite controlSite;
	try {
		OleFrame frame = new OleFrame(shell, SWT.NONE);
		controlSite = new OleControlSite(frame, SWT.NONE, "Shell.Explorer");
		controlSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
	} catch (SWTError e) {
		System.out.println("Unable to open activeX control");
		return;
	}
	
	// IWebBrowser
	final OleAutomation webBrowser = new OleAutomation(controlSite);

	// When the document is loaded, access the document object for the new page
	// and evalute expression using Script.
	int DownloadComplete = 104;
	controlSite.addEventListener(DownloadComplete, new OleListener() {
		public void handleEvent(OleEvent event) {
			int[] htmlDocumentID = webBrowser.getIDsOfNames(new String[]{"Document"}); 
			if (htmlDocumentID == null) return;
			Variant pVarResult = webBrowser.getProperty(htmlDocumentID[0]);
			if (pVarResult == null || pVarResult.getType() == 0) return;
			//IHTMLDocument2
			OleAutomation htmlDocument = null;
			try {
				htmlDocument = pVarResult.getAutomation();
				pVarResult.dispose();
	
				int[] scriptID = htmlDocument.getIDsOfNames(new String[]{"Script"}); 
				if (scriptID == null) return;
				pVarResult = htmlDocument.getProperty(scriptID[0]);
				if (pVarResult == null || pVarResult.getType() == 0) return;
				OleAutomation htmlWindow = null;
				try {
					//IHTMLWindow2
					htmlWindow = pVarResult.getAutomation();
					pVarResult.dispose();
					int[] evaluateID = htmlWindow.getIDsOfNames(new String[] {"evaluate"});
					if (evaluateID == null) return;
					String expression = "5+Math.sin(9)";
					Variant[] rgvarg = new Variant[] {new Variant(expression)};
					pVarResult = htmlWindow.invoke(evaluateID[0], rgvarg, null);
					if (pVarResult == null || pVarResult.getType() == 0) return;
					System.out.println(expression+" ="+pVarResult.getString());
				} finally {
					htmlWindow.dispose();
				}
			} finally {
				htmlDocument.dispose();
			}
		}
	});
	
	// Navigate to a web site
	int[] ids = webBrowser.getIDsOfNames(new String[]{"Navigate", "URL"}); 
	Variant[] rgvarg = new Variant[] {new Variant("http://dev.eclipse.org/viewcvs/index.cgi/%7Echeckout%7E/org.eclipse.swt.snippets/src/org/eclipse/swt/snippets/Snippet187.html")};
	int[] rgdispidNamedArgs = new int[]{ids[1]};
	webBrowser.invoke(ids[0], rgvarg, rgdispidNamedArgs);

	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	//Remember to release OleAutomation Object
	webBrowser.dispose();
	display.dispose();
	
}
}', now(), now());
insert into SNIPPET values (10098, 1, '[SWT]SnippetSnippet188.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/*
 * Scroll a widget into view on focus in
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */

public class Snippet188 {
	
public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new GridLayout());
	final ScrolledComposite sc = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	sc.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
	Composite c = new Composite(sc, SWT.NONE);
	c.setLayout(new GridLayout(10, true));
	for (int i = 0 ; i < 300; i++) {
		Button b = new Button(c, SWT.PUSH);
		b.setText("Button "+i);
	}
	sc.setContent(c);
	sc.setExpandHorizontal(true);
	sc.setExpandVertical(true);
	sc.setMinSize(c.computeSize(SWT.DEFAULT, SWT.DEFAULT));
	sc.setShowFocusedControl(true);
	
	shell.setSize(300, 500);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10099, 1, '[SWT]SnippetSnippet189.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/*
 * Text with underline and strike through
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */

public class Snippet189 {
	
public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setText("StyledText with underline and strike through");
	shell.setLayout(new FillLayout());
	StyledText text = new StyledText (shell, SWT.BORDER);
	text.setText("0123456789 ABCDEFGHIJKLM NOPQRSTUVWXYZ");
	// make 0123456789 appear underlined
	StyleRange style1 = new StyleRange();
	style1.start = 0;
	style1.length = 10;
	style1.underline = true;
	text.setStyleRange(style1);
	// make ABCDEFGHIJKLM have a strike through
	StyleRange style2 = new StyleRange();
	style2.start = 11;
	style2.length = 13;
	style2.strikeout = true;
	text.setStyleRange(style2);
	// make NOPQRSTUVWXYZ appear underlined and have a strike through
	StyleRange style3 = new StyleRange();
	style3.start = 25;
	style3.length = 13;
	style3.underline = true;
	style3.strikeout = true;
	text.setStyleRange(style3);
	shell.pack();
	shell.open();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10100, 1, '[SWT]SnippetSnippet19.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Text example snippet: verify input (only allow digits)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet19 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Text text = new Text (shell, SWT.BORDER | SWT.V_SCROLL);
	text.setBounds (10, 10, 200, 200);
	text.addListener (SWT.Verify, new Listener () {
		public void handleEvent (Event e) {
			String string = e.text;
			char [] chars = new char [string.length ()];
			string.getChars (0, chars.length, chars, 0);
			for (int i=0; i<chars.length; i++) {
				if (!(0 <= chars [i] && chars [i] <= 9)) {
					e.doit = false;
					return;
				}
			}
		}
	});
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10101, 1, '[SWT]SnippetSnippet190.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/*
 * Floating point values in Spinner
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */

public class Snippet190 {
	
public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setText("Spinner with float values");
	shell.setLayout(new GridLayout());
	final Spinner spinner = new Spinner(shell, SWT.NONE);
	// allow 3 decimal places
	spinner.setDigits(3);
	// set the minimum value to 0.001
	spinner.setMinimum(1);
	// set the maximum value to 20
	spinner.setMaximum(20000);
	// set the increment value to 0.010
	spinner.setIncrement(10);
	// set the seletion to 3.456
	spinner.setSelection(3456);
	spinner.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			int selection = spinner.getSelection();
			int digits = spinner.getDigits();
			System.out.println("Selection is "+(selection / Math.pow(10, digits)));
		}
	});
	shell.setSize(200, 200);
	shell.open();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10102, 1, '[SWT]SnippetSnippet191.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/*
 * Detect when the user scrolls a text control
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

public class Snippet191 {
public static void main(String[] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	final Text text = new Text (shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	for (int i=0; i<32; i++) {
		text.append (i + "-This is a line of text in a widget-" + i + "\n");
	}
	text.setSelection (0);
	Listener listener = new Listener () {
		int lastIndex = text.getTopIndex ();
		public void handleEvent (Event e) {
			int index = text.getTopIndex ();
			if (index != lastIndex) {
				lastIndex = index;
				System.out.println ("Scrolled, topIndex=" + index);
			}
		}
	};
	/* NOTE: Only detects scrolling by the user */
	text.addListener (SWT.MouseDown, listener);
	text.addListener (SWT.MouseMove, listener);
	text.addListener (SWT.MouseUp, listener);
	text.addListener (SWT.KeyDown, listener);
	text.addListener (SWT.KeyUp, listener);
	text.addListener (SWT.Resize, listener);
	ScrollBar hBar = text.getHorizontalBar();
	if (hBar != null) {
		hBar.addListener (SWT.Selection, listener);
	}
	ScrollBar vBar = text.getVerticalBar();
	if (vBar != null) {
		vBar.addListener (SWT.Selection, listener);
	}
	shell.pack ();
	Point size = shell.computeSize (SWT.DEFAULT, SWT.DEFAULT);
	shell.setSize (size. x - 32, size.y / 2);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10103, 1, '[SWT]SnippetSnippet192.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import java.util.*;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/*
 * Show a sort indicator in the column header
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

public class Snippet192 {
public static void main(String[] args) {
	// initialize data with keys and random values
	int size = 100;
	Random random = new Random();
	final int[][] data = new int[size][];
	for (int i = 0; i < data.length; i++) {
		data[i] = new int[] {i, random.nextInt()};
	}
	// create a virtual table to display data
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	final Table table = new Table(shell, SWT.VIRTUAL);
	table.setHeaderVisible(true);
	table.setLinesVisible(true);
	table.setItemCount(size);
	final TableColumn column1 = new TableColumn(table, SWT.NONE);
	column1.setText("Key");
	column1.setWidth(200);
	final TableColumn column2 = new TableColumn(table, SWT.NONE);
	column2.setText("Value");
	column2.setWidth(200);
	table.addListener(SWT.SetData, new Listener() {
		public void handleEvent(Event e) {
			TableItem item = (TableItem) e.item;
			int index = table.indexOf(item);
			int[] datum = data[index];
			item.setText(new String[] {Integer.toString(datum[0]),
					Integer.toString(datum[1]) });
		}
	});
	// Add sort indicator and sort data when column selected
	Listener sortListener = new Listener() {
		public void handleEvent(Event e) {
			// determine new sort column and direction
			TableColumn sortColumn = table.getSortColumn();
			TableColumn currentColumn = (TableColumn) e.widget;
			int dir = table.getSortDirection();
			if (sortColumn == currentColumn) {
				dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
			} else {
				table.setSortColumn(currentColumn);
				dir = SWT.UP;
			}
			// sort the data based on column and direction
			final int index = currentColumn == column1 ? 0 : 1;
			final int direction = dir;
			Arrays.sort(data, new Comparator() {
				public int compare(Object arg0, Object arg1) {
					int[] a = (int[]) arg0;
					int[] b = (int[]) arg1;
					if (a[index] == b[index]) return 0;
					if (direction == SWT.UP) {
						return a[index] < b[index] ? -1 : 1;
					}
					return a[index] < b[index] ? 1 : -1;
				}
			});
			// update data displayed in table
			table.setSortDirection(dir);
			table.clearAll();
		}
	};
	column1.addListener(SWT.Selection, sortListener);
	column2.addListener(SWT.Selection, sortListener);
	table.setSortColumn(column1);
	table.setSortDirection(SWT.UP);
	shell.setSize(shell.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, 300);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}', now(), now());
insert into SNIPPET values (10104, 1, '[SWT]SnippetSnippet193.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tree example snippet: allow user to reorder columns by dragging and programmatically.
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet193 {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new RowLayout(SWT.HORIZONTAL));
		final Tree tree = new Tree(shell, SWT.BORDER | SWT.CHECK);
		tree.setLayoutData(new RowData(-1, 300));
		tree.setHeaderVisible(true);
		TreeColumn column = new TreeColumn(tree, SWT.LEFT);
		column.setText("Column 0");
		column = new TreeColumn(tree, SWT.CENTER);
		column.setText("Column 1");
		column = new TreeColumn(tree, SWT.LEFT);
		column.setText("Column 2");
		column = new TreeColumn(tree, SWT.RIGHT);
		column.setText("Column 3");
		column = new TreeColumn(tree, SWT.CENTER);
		column.setText("Column 4");
		for (int i = 0; i < 5; i++) {
			TreeItem item = new TreeItem(tree, SWT.NONE);
			String[] text = new String[]{i+":0", i+":1", i+":2", i+":3", i+":4"};
			item.setText(text);
			for (int j = 0; j < 5; j++) {
				TreeItem subItem = new TreeItem(item, SWT.NONE);
				text = new String[]{i+","+j+":0", i+","+j+":1", i+","+j+":2", i+","+j+":3", i+","+j+":4"};
				subItem.setText(text);
				for (int k = 0; k < 5; k++) {
					TreeItem subsubItem = new TreeItem(subItem, SWT.NONE);
					text = new String[]{i+","+j+","+k+":0", i+","+j+","+k+":1", i+","+j+","+k+":2", i+","+j+","+k+":3", i+","+j+","+k+":4"};
					subsubItem.setText(text);
				}
			}
		}
		Listener listener = new Listener() {
			public void handleEvent(Event e) {
				System.out.println("Move "+e.widget);
			}
		};
		TreeColumn[] columns = tree.getColumns();
		for (int i = 0; i < columns.length; i++) {
			columns[i].setWidth(100);
			columns[i].setMoveable(true);
			columns[i].addListener(SWT.Move, listener);
		}
		Button b = new Button(shell, SWT.PUSH);
		b.setText("invert column order");
		b.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				int[] order = tree.getColumnOrder();
				for (int i = 0; i < order.length / 2; i++) {
					int temp = order[i];
					order[i] = order[order.length - i - 1];
					order[order.length - i - 1] = temp;
				}
				tree.setColumnOrder(order);
			}
		});
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10105, 1, '[SWT]SnippetSnippet194.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Write an animated GIF to a file.
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet194 {
	Display display;
	Color white, red, green, blue;
	PaletteData palette;
	Font font;

	public static void main(String[] args) {
		new Snippet194().run();
	}
	
	public void run() {
		display = new Display();
		RGB whiteRGB = new RGB(0xff, 0xff, 0xff);
		RGB redRGB = new RGB(0xff, 0, 0);
		RGB greenRGB = new RGB(0, 0xff, 0);
		RGB blueRGB = new RGB(0, 0, 0xff);
		palette = new PaletteData(new RGB[] {
				whiteRGB,	// 0
				redRGB,		// 1
				greenRGB,	// 2
				blueRGB });	// 3
		white = new Color(display, whiteRGB);
		red = new Color(display, redRGB);
		green = new Color(display, greenRGB);
		blue = new Color(display, blueRGB);
		font = new Font(display, "Comic Sans MS", 24, SWT.BOLD);
		
		ImageData[] data = new ImageData[4];
		data[0] = newFrame("",  white, false, 0, 0, 101, 55, SWT.DM_FILL_NONE, 40);
		data[1] = newFrame("S", red,   true,  0, 0,  30, 55, SWT.DM_FILL_NONE, 40);
		data[2] = newFrame("W", green, true, 28, 0,  39, 55, SWT.DM_FILL_NONE, 40);
		data[3] = newFrame("T", blue,  true, 69, 0,  32, 55, SWT.DM_FILL_BACKGROUND, 200);
		
		ImageLoader loader = new ImageLoader();
		loader.data = data;
		loader.backgroundPixel = 0;
		loader.logicalScreenHeight = data[0].height;
		loader.logicalScreenWidth = data[0].width;
		loader.repeatCount = 0; // run forever
		loader.save("swt.gif", SWT.IMAGE_GIF);

		white.dispose();
		red.dispose();
		green.dispose();
		blue.dispose();
		font.dispose();
		display.dispose();
	}

	ImageData newFrame(String letter, Color color, boolean transparent, int x, int y, int width, int height, int disposalMethod, int delayTime) {
		ImageData temp = new ImageData(width, height, 2, palette); // 4-color palette has depth 2
		Image image = new Image(display, temp);
		GC gc = new GC(image);
		gc.setBackground(white);
		gc.fillRectangle(0, 0, width, height);
		gc.setForeground(color);
		gc.setFont(font);
		gc.drawString(letter, 5, 5);
		gc.dispose();
		ImageData frame = image.getImageData();
		if (transparent) frame.transparentPixel = 0; // white
		image.dispose();
		frame.x = x;
		frame.y = y;
		frame.disposalMethod = disposalMethod;
		frame.delayTime = delayTime;
		return frame;
	}
}
', now(), now());
insert into SNIPPET values (10106, 1, '[SWT]SnippetSnippet195.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * SWT OpenGL snippet: use LWJGL to draw to an SWT GLCanvas
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.glu.GLU;
import org.lwjgl.LWJGLException;

public class Snippet195 {
	static void drawTorus(float r, float R, int nsides, int rings) {
		float ringDelta = 2.0f * (float) Math.PI / rings;
		float sideDelta = 2.0f * (float) Math.PI / nsides;
		float theta = 0.0f, cosTheta = 1.0f, sinTheta = 0.0f;
		for (int i = rings - 1; i >= 0; i--) {
			float theta1 = theta + ringDelta;
			float cosTheta1 = (float) Math.cos(theta1);
			float sinTheta1 = (float) Math.sin(theta1);
			GL11.glBegin(GL11.GL_QUAD_STRIP);
			float phi = 0.0f;
			for (int j = nsides; j >= 0; j--) {
				phi += sideDelta;
				float cosPhi = (float) Math.cos(phi);
				float sinPhi = (float) Math.sin(phi);
				float dist = R + r * cosPhi;
				GL11.glNormal3f(cosTheta1 * cosPhi, -sinTheta1 * cosPhi, sinPhi);
				GL11.glVertex3f(cosTheta1 * dist, -sinTheta1 * dist, r * sinPhi);
				GL11.glNormal3f(cosTheta * cosPhi, -sinTheta * cosPhi, sinPhi);
				GL11.glVertex3f(cosTheta * dist, -sinTheta * dist, r * sinPhi);
			}
			GL11.glEnd();
			theta = theta1;
			cosTheta = cosTheta1;
			sinTheta = sinTheta1;
		}
	}

	public static void main(String [] args) {
		final Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		Composite comp = new Composite(shell, SWT.NONE);
		comp.setLayout(new FillLayout());
		GLData data = new GLData ();
		data.doubleBuffer = true;
		final GLCanvas canvas = new GLCanvas(comp, SWT.NONE, data);

		canvas.setCurrent();
		try {
			GLContext.useContext(canvas);
		} catch(LWJGLException e) { e.printStackTrace(); }

		canvas.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				Rectangle bounds = canvas.getBounds();
				float fAspect = (float) bounds.width / (float) bounds.height;
				canvas.setCurrent();
				try {
					GLContext.useContext(canvas);
				} catch(LWJGLException e) { e.printStackTrace(); }
				GL11.glViewport(0, 0, bounds.width, bounds.height);
				GL11.glMatrixMode(GL11.GL_PROJECTION);
				GL11.glLoadIdentity();
				GLU.gluPerspective(45.0f, fAspect, 0.5f, 400.0f);
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glLoadIdentity();
			}
		});

		GL11.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		GL11.glColor3f(1.0f, 0.0f, 0.0f);
		GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT, GL11.GL_NICEST);
		GL11.glClearDepth(1.0);
		GL11.glLineWidth(2);
		GL11.glEnable(GL11.GL_DEPTH_TEST);

		shell.setText("SWT/LWJGL Example");
		shell.setSize(640, 480);
		shell.open();

		display.asyncExec(new Runnable() {
			int rot = 0;
			public void run() {
				if (!canvas.isDisposed()) {
					canvas.setCurrent();
					try {
						GLContext.useContext(canvas);
					} catch(LWJGLException e) { e.printStackTrace(); }
					GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
					GL11.glClearColor(.3f, .5f, .8f, 1.0f);
					GL11.glLoadIdentity();
					GL11.glTranslatef(0.0f, 0.0f, -10.0f);
					float frot = rot;
					GL11.glRotatef(0.15f * rot, 2.0f * frot, 10.0f * frot, 1.0f);
					GL11.glRotatef(0.3f * rot, 3.0f * frot, 1.0f * frot, 1.0f);
					rot++;
					GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
					GL11.glColor3f(0.9f, 0.9f, 0.9f);
					drawTorus(1, 1.9f + ((float) Math.sin((0.004f * frot))), 15, 15);
					canvas.swapBuffers();
					display.asyncExec(this);
				}
			}
		});

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10107, 1, '[SWT]SnippetSnippet196.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Text example snippet: use a regular expression to verify input
 * In this case a phone number is used.
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import java.util.regex.*;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet196 {
	/*
	 * Phone numbers follow the rule [(][1-9][1-9][1-9][)][1-9][1-9][1-9][-][1-9][1-9][1-9][1-9]
	 */
	private static final String REGEX = "[(]\\d{3}[)]\\d{3}[-]\\d{4}";  //$NON-NLS-1$
	private static final String template = "(###)###-####"; //$NON-NLS-1$
	private static final String defaultText = "(000)000-0000"; //$NON-NLS-1$
	
	
public static void main(String[] args) {
	
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new GridLayout());
	final Text text = new Text(shell, SWT.BORDER);
	Font font = new Font(display, "Courier New", 10, SWT.NONE); //$NON-NLS-1$
	text.setFont(font);
	text.setText(template);	
	text.addListener(SWT.Verify, new Listener() {
		//create the pattern for verification
		Pattern pattern = Pattern.compile(REGEX);	
		//ignore event when caused by inserting text inside event handler
		boolean ignore;
		public void handleEvent(Event e) {
			if (ignore) return;
			e.doit = false;
			if (e.start > 13 || e.end > 14) return;
			StringBuffer buffer = new StringBuffer(e.text);
			
			//handle backspace
			if (e.character == \b) {
				for (int i = e.start; i < e.end; i++) {
					// skip over separators
					switch (i) {
						case 0: 
							if (e.start + 1 == e.end) {
								return;
							} else {
								buffer.append(();
							}
							break;
						case 4:
							if (e.start + 1 == e.end) {
								buffer.append(new char [] {#,)});
								e.start--;
							} else {
								buffer.append());
							}
							break;
						case 8:
							if (e.start + 1 == e.end) {
								buffer.append(new char [] {#,-});
								e.start--;
							} else {
								buffer.append(-);
							}
							break;
						default: buffer.append(#);
					}
				}
				text.setSelection(e.start, e.start + buffer.length());
				ignore = true;
				text.insert(buffer.toString());
				ignore = false;
				// move cursor backwards over separators
				if (e.start == 5 || e.start == 9) e.start--;
				text.setSelection(e.start, e.start);
				return;
			}
			
			StringBuffer newText = new StringBuffer(defaultText);
			char[] chars = e.text.toCharArray();
			int index = e.start - 1;
			for (int i = 0; i < e.text.length(); i++) {
				index++;
				switch (index) {
					case 0:
						if (chars[i] == () continue;
						index++;
						break;
					case 4:
						if (chars[i] == )) continue;
						index++;
						break;
					case 8:
						if (chars[i] == -) continue;
						index++;
						break;
				}
				if (index >= newText.length()) return;
				newText.setCharAt(index, chars[i]);
			}
			// if text is selected, do not paste beyond range of selection
			if (e.start < e.end && index + 1 != e.end) return;
			Matcher matcher = pattern.matcher(newText);
			if (matcher.lookingAt()) {
				text.setSelection(e.start, index + 1);
				ignore = true;
				text.insert(newText.substring(e.start, index + 1));
				ignore = false;
			}			
		}
	});
		
	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	font.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10108, 1, '[SWT]SnippetSnippet197.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Draw wrapped text using TextLayout
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet197 {
	final static String longString = "The preferred size of a widget is the minimum size needed to show its content. In the case of a Composite, the preferred size is the smallest rectangle that contains all of its children. If children have been positioned by the application, the Composite computes its own preferred size based on the size and position of the children. If a Composite is using a layout class to position its children, it asks the Layout to compute the size of its clientArea, and then it adds in the trim to determine its preferred size.";
public static void main(String[] args) {
	Display display = new Display();
	final Shell shell = new Shell(display);
	final TextLayout layout = new TextLayout(display);
	layout.setText(longString);
	Listener listener = new Listener() {
		public void handleEvent (Event event) {
			switch (event.type) {
			case SWT.Paint:
				layout.draw(event.gc, 10, 10);
				break;
			case SWT.Resize:
				layout.setWidth(shell.getSize().x - 20);
				break;
			}
		}
	};
	shell.addListener(SWT.Paint, listener);
	shell.addListener(SWT.Resize, listener);
	shell.setSize(300, 300);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10109, 1, '[SWT]SnippetSnippet198.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Create a path from some text
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet198 {
public static void main(String[] args) {
	Display display = new Display();
	FontData data = display.getSystemFont().getFontData()[0];
	Font font = new Font(display, data.getName(), 96, SWT.BOLD | SWT.ITALIC);
	final Color green = display.getSystemColor(SWT.COLOR_GREEN);
	final Color blue = display.getSystemColor(SWT.COLOR_BLUE);
	final Path path;
	try {
		path = new Path(display);
		path.addString("SWT", 0, 0, font);
	} catch (SWTException e) {
		//Advanced Graphics not supported.  
		//This new API requires the Cairo Vector engine on GTK and Motif and GDI+ on Windows.
		System.out.println(e.getMessage());
		return;
	}
	Shell shell = new Shell(display);
	shell.addListener(SWT.Paint, new Listener() {
		public void handleEvent(Event e) {			
			GC gc = e.gc;
			gc.setBackground(green);
			gc.setForeground(blue);
			gc.fillPath(path);
			gc.drawPath(path);
		}
	});
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	path.dispose();
	font.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10110, 1, '[SWT]SnippetSnippet199.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Listen for events in Excel (win32 only)
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.widgets.*;

public class Snippet199 {
	static String IID_AppEvents = "{00024413-0000-0000-C000-000000000046}";
	// Event ID
	static int NewWorkbook            = 0x0000061d;
	static int SheetSelectionChange   = 0x00000616;
	static int SheetBeforeDoubleClick = 0x00000617;
	static int SheetBeforeRightClick  = 0x00000618;
	static int SheetActivate          = 0x00000619;
	static int SheetDeactivate        = 0x0000061a;
	static int SheetCalculate         = 0x0000061b;
	static int SheetChange            = 0x0000061c;
	static int WorkbookOpen           = 0x0000061f;
	static int WorkbookActivate       = 0x00000620;
	static int WorkbookDeactivate     = 0x00000621;
	static int WorkbookBeforeClose    = 0x00000622;
	static int WorkbookBeforeSave     = 0x00000623;
	static int WorkbookBeforePrint    = 0x00000624;
	static int WorkbookNewSheet       = 0x00000625;
	static int WorkbookAddinInstall   = 0x00000626;
	static int WorkbookAddinUninstall = 0x00000627;
	static int WindowResize           = 0x00000612;
	static int WindowActivate         = 0x00000614;
	static int WindowDeactivate       = 0x00000615;
	static int SheetFollowHyperlink   = 0x0000073e;

 public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	OleControlSite controlSite;
	try {
		OleFrame frame = new OleFrame(shell, SWT.NONE);
		controlSite = new OleControlSite(frame, SWT.NONE, "Excel.Sheet");
		controlSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
	} catch (SWTError e) {
		System.out.println("Unable to open activeX control");
		return;
	}
	shell.open();

	OleAutomation excelSheet = new OleAutomation(controlSite);
	int[] dispIDs = excelSheet.getIDsOfNames(new String[] {"Application"});
	Variant pVarResult = excelSheet.getProperty(dispIDs[0]);
	OleAutomation application = pVarResult.getAutomation();
	pVarResult.dispose();
	excelSheet.dispose();
	
	int eventID = SheetSelectionChange;
	OleListener listener = new OleListener() {
		public void handleEvent (OleEvent e) {
			System.out.println("selection has changed");
			// two arguments which must be released (row and column)
			Variant[] args = e.arguments;
			for (int i = 0; i < args.length; i++) {
				System.out.println(args[i]);
				args [i].dispose();
			}
		}
	};
	controlSite.addEventListener(application, IID_AppEvents, eventID, listener);
	
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	application.dispose();
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10111, 1, '[SWT]SnippetSnippet2.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table example snippet: sort a table by column
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import java.text.*;
import java.util.*;

public class Snippet2 {

public static void main (String [] args) {
    Display display = new Display ();
    Shell shell = new Shell (display);
    shell.setLayout(new FillLayout());
    final Table table = new Table(shell, SWT.BORDER);
    table.setHeaderVisible(true);
    final TableColumn column1 = new TableColumn(table, SWT.NONE);
    column1.setText("Column 1");
    final TableColumn column2 = new TableColumn(table, SWT.NONE);
    column2.setText("Column 2");
    TableItem item = new TableItem(table, SWT.NONE);
    item.setText(new String[] {"a", "3"});
    item = new TableItem(table, SWT.NONE);
    item.setText(new String[] {"b", "2"});
    item = new TableItem(table, SWT.NONE);
    item.setText(new String[] {"c", "1"});
    column1.setWidth(100);
    column2.setWidth(100);
    Listener sortListener = new Listener() {
        public void handleEvent(Event e) {
            TableItem[] items = table.getItems();
            Collator collator = Collator.getInstance(Locale.getDefault());
            TableColumn column = (TableColumn)e.widget;
            int index = column == column1 ? 0 : 1;
            for (int i = 1; i < items.length; i++) {
                String value1 = items[i].getText(index);
                for (int j = 0; j < i; j++){
                    String value2 = items[j].getText(index);
                    if (collator.compare(value1, value2) < 0) {
                        String[] values = {items[i].getText(0), items[i].getText(1)};
                        items[i].dispose();
                        TableItem item = new TableItem(table, SWT.NONE, j);
                        item.setText(values);
                        items = table.getItems();
                        break;
                    }
                }
            }
            table.setSortColumn(column);
        }
    };
    column1.addListener(SWT.Selection, sortListener);
    column2.addListener(SWT.Selection, sortListener);
    table.setSortColumn(column1);
    table.setSortDirection(SWT.UP);
    shell.setSize(shell.computeSize(SWT.DEFAULT, SWT.DEFAULT).x, 300);
    shell.open();
    while (!shell.isDisposed ()) {
        if (!display.readAndDispatch ()) display.sleep ();
    }
    display.dispose ();
}

}
', now(), now());
insert into SNIPPET values (10112, 1, '[SWT]SnippetSnippet20.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * CoolBar example snippet: create a cool bar
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet20 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	CoolBar bar = new CoolBar (shell, SWT.BORDER);
	for (int i=0; i<2; i++) {
		CoolItem item = new CoolItem (bar, SWT.NONE);
		Button button = new Button (bar, SWT.PUSH);
		button.setText ("Button " + i);
		Point size = button.computeSize (SWT.DEFAULT, SWT.DEFAULT);
		item.setPreferredSize (item.computeSize (size.x, size.y));
		item.setControl (button);
	}
	bar.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10113, 1, '[SWT]SnippetSnippet200.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Fill a shape with a predefined pattern
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet200 {
public static void main(String[] args) {
	Display display = new Display();
	//define a pattern on an image
	final Image image = new Image(display, 1000, 1000);
	Color blue = display.getSystemColor(SWT.COLOR_BLUE);
	Color yellow = display.getSystemColor(SWT.COLOR_YELLOW);
	Color white = display.getSystemColor(SWT.COLOR_WHITE);
	GC gc = new GC(image);
	gc.setBackground(white);
	gc.setForeground(yellow);
	gc.fillGradientRectangle(0, 0, 1000, 1000, true);
	for (int i=-500; i<1000; i+=10) {
		gc.setForeground(blue);
		gc.drawLine(i, 0, 500 + i, 1000);
		gc.drawLine(500 + i, 0, i, 1000);
	}	
	gc.dispose();
	final Pattern pattern;
	try {
		pattern = new Pattern(display, image);
	} catch (SWTException e) {
		//Advanced Graphics not supported.  
		//This new API requires the Cairo Vector engine on GTK and Motif and GDI+ on Windows.
		System.out.println(e.getMessage());
		return;
	}
	
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	Composite c = new Composite(shell, SWT.DOUBLE_BUFFERED);
	c.addListener(SWT.Paint, new Listener() {
		public void handleEvent(Event event) {
			Rectangle r = ((Composite)event.widget).getClientArea();
			GC gc = event.gc;
			gc.setBackgroundPattern(pattern);
			gc.fillOval(5, 5, r.width - 10, r.height - 10);	
		}
	});
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	image.dispose();
	pattern.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10114, 1, '[SWT]SnippetSnippet201.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Virtual Table example snippet: create a table with 1,000,000 items (lazy, page size 64)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */ 
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet201 {

static final int PAGE_SIZE = 64;
static final int COUNT = 100000;

public static void main(String[] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setLayout (new RowLayout (SWT.VERTICAL));
	final Table table = new Table (shell, SWT.VIRTUAL | SWT.BORDER);
	table.addListener (SWT.SetData, new Listener () {
		public void handleEvent (Event event) {
			TableItem item = (TableItem) event.item;
			int index = table.indexOf (item);
			int start = index / PAGE_SIZE * PAGE_SIZE;
			int end = Math.min (start + PAGE_SIZE, table.getItemCount ());
			for (int i = start; i < end; i++) {
				item = table.getItem (i);
				item.setText ("Item " + i);
			}
		}
	});
	table.setLayoutData (new RowData (200, 200));
	Button button = new Button (shell, SWT.PUSH);
	button.setText ("Add Items");
	final Label label = new Label(shell, SWT.NONE);
	button.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event event) {
			long t1 = System.currentTimeMillis ();
			table.setItemCount (COUNT);
			long t2 = System.currentTimeMillis ();
			label.setText ("Items: " + COUNT + ", Time: " + (t2 - t1) + " (ms) [page=" + PAGE_SIZE + "]");
			shell.layout ();
		}
	});
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10115, 1, '[SWT]SnippetSnippet202.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Virtual Tree example snippet: populate tree lazily
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */ 
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet202 {

public static void main(String[] args) {
	Display display = new Display();
	final Shell shell = new Shell(display);
	shell.setLayout (new FillLayout());
	final Tree tree = new Tree(shell, SWT.VIRTUAL | SWT.BORDER);
	tree.addListener(SWT.SetData, new Listener() {
		public void handleEvent(Event event) {
			TreeItem item = (TreeItem)event.item;
			TreeItem parentItem = item.getParentItem();
			String text = null;
			if (parentItem == null) {
				text = "node "+tree.indexOf(item);
			} else {
				text = parentItem.getText()+" - "+parentItem.indexOf(item);
			}
			item.setText(text);
			item.setItemCount(10);
		}
	});
	tree.setItemCount(20);
	shell.setSize(400, 300);
	shell.open();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10116, 1, '[SWT]SnippetSnippet203.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * TextLayout example snippet: using TextLayout justify, alignment and indent 
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */ 
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

public class Snippet203 {
	
public static void main(String[] args) {
	Display display = new Display();
	final Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.DOUBLE_BUFFERED);	
	shell.setText("Indent, Justify, Align");
	String[] texts = {
		"Plans do not materialize out of nowhere, nor are they entirely static. To ensure the planning process is transparent and open to the entire Eclipse community, we (the Eclipse PMC) post plans in an embryonic form and revise them throughout the release cycle.",
		"The first part of the plan deals with the important matters of release deliverables, release milestones, target operating environments, and release-to-release compatibility. These are all things that need to be clear for any release, even if no features were to change.",
		"The remainder of the plan consists of plan items for the various Eclipse subprojects. Each plan item covers a feature or API that is to be added to Eclipse, or some aspect of Eclipse that is to be improved. Each plan item has its own entry in the Eclipse bugzilla database, with a title and a concise summary (usually a single paragraph) that explains the work item at a suitably high enough level so that everyone can readily understand what the work item is without having to understand the nitty-gritty detail.",
	};
	int[] alignments = {SWT.LEFT, SWT.CENTER, SWT.RIGHT};
	final TextLayout[] layouts = new TextLayout[texts.length];
	for (int i = 0; i < layouts.length; i++) {
		TextLayout layout = new TextLayout(display);
		layout.setText(texts[i]);
		layout.setIndent(30);
		layout.setJustify(true);
		layout.setAlignment(alignments[i]);		
		layouts[i] = layout;
	}
	shell.addListener(SWT.Paint, new Listener() {
		public void handleEvent(Event event) {
			Point point = new Point(10, 10);
			int width = shell.getClientArea().width - 2 * point.x;
			for (int i = 0; i < layouts.length; i++) {
				TextLayout layout = layouts[i];
				layout.setWidth(width);
				layout.draw(event.gc, point.x, point.y);
				point.y += layout.getBounds().height + 10;
			}			
		}
	});
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	for (int i = 0; i < layouts.length; i++) {
		layouts[i].dispose();		
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10117, 1, '[SWT]SnippetSnippet204.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * TextLayout example snippet: using the rise field of a TextStyle.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */ 
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

public class Snippet204 {
	
public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.DOUBLE_BUFFERED);
	shell.setText("Modify Rise");
	FontData data = display.getSystemFont().getFontData()[0];
	Font font = new Font(display, data.getName(), 24, SWT.NORMAL);
	Font smallFont = new Font(display, data.getName(), 8, SWT.NORMAL);
	GC gc = new GC(shell);
	gc.setFont(smallFont);
	FontMetrics smallMetrics = gc.getFontMetrics();
	final int smallBaseline = smallMetrics.getAscent() + smallMetrics.getLeading();
	gc.setFont(font);
	FontMetrics metrics = gc.getFontMetrics();
	final int baseline = metrics.getAscent() + metrics.getLeading();
	gc.dispose();
	
	final TextLayout layout0 = new TextLayout(display);
	layout0.setText("SubscriptScriptSuperscript");
	layout0.setFont(font);
	TextStyle subscript0 = new TextStyle(smallFont, null, null);
	TextStyle superscript0 = new TextStyle(smallFont, null, null);
	superscript0.rise = baseline - smallBaseline;
	layout0.setStyle(subscript0, 0, 8);
	layout0.setStyle(superscript0, 15, 25);
		
	final TextLayout layout1 = new TextLayout(display);
	layout1.setText("SubscriptScriptSuperscript");
	layout1.setFont(font);
	TextStyle subscript1 = new TextStyle(smallFont, null, null);
	subscript1.rise = -smallBaseline;
	TextStyle superscript1 = new TextStyle(smallFont, null, null);
	superscript1.rise = baseline;
	layout1.setStyle(subscript1, 0, 8);
	layout1.setStyle(superscript1, 15, 25);
	
	shell.addListener(SWT.Paint, new Listener() {
		public void handleEvent(Event event) {
			Display display = event.display;
			GC gc = event.gc;
			
			Rectangle rect0 = layout0.getBounds();
			rect0.x += 10;
			rect0.y += 10;
			gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
			gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
			gc.fillRectangle(rect0);
			layout0.draw(gc, rect0.x, rect0.y);
			gc.setForeground(display.getSystemColor(SWT.COLOR_MAGENTA));
			gc.drawLine(rect0.x, rect0.y, rect0.x + rect0.width, rect0.y);
			gc.drawLine(rect0.x, rect0.y + baseline, rect0.x + rect0.width, rect0.y + baseline);
			gc.drawLine(rect0.x + rect0.width / 2, rect0.y, rect0.x + rect0.width / 2, rect0.y + rect0.height);
			
			Rectangle rect1 = layout1.getBounds();
			rect1.x += 10;
			rect1.y += 20 + rect0.height;
			gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
			gc.setForeground(display.getSystemColor(SWT.COLOR_BLACK));
			gc.fillRectangle(rect1);			
			layout1.draw(gc, rect1.x, rect1.y);
			
			gc.setForeground(display.getSystemColor(SWT.COLOR_MAGENTA));
			gc.drawLine(rect1.x, rect1.y + smallBaseline, rect1.x + rect1.width, rect1.y + smallBaseline);
			gc.drawLine(rect1.x, rect1.y + baseline + smallBaseline, rect1.x + rect1.width, rect1.y + baseline + smallBaseline);
			gc.drawLine(rect1.x + rect1.width / 2, rect1.y, rect1.x + rect1.width / 2, rect1.y + rect1.height);
		}
	});
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	layout0.dispose();
	layout1.dispose();
	smallFont.dispose();
	font.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10118, 1, '[SWT]SnippetSnippet205.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * TextLayout example snippet: using the GlyphMetrics to embedded images in 
 * a TextLayout. 
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */ 
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;


public class Snippet205 {
	
public static void main(String[] args) {
	Display display = new Display();
	final Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.DOUBLE_BUFFERED);
	shell.setText("Embedding objects in text");
	final Image[] images = {new Image(display, 32, 32), new Image(display, 20, 40), new Image(display, 40, 20)};
	int[] colors  = {SWT.COLOR_BLUE, SWT.COLOR_MAGENTA, SWT.COLOR_GREEN};
	for (int i = 0; i < images.length; i++) {
		GC gc = new GC(images[i]);
		gc.setBackground(display.getSystemColor(colors[i]));
		gc.fillRectangle(images[i].getBounds());
		gc.dispose();
	}
	
	final Button button = new Button(shell, SWT.PUSH);
	button.setText("Button");
	button.pack();
	String text = "Here is some text with a blue image \uFFFC, a magenta image \uFFFC, a green image \uFFFC, and a button: \uFFFC.";
	final int[] imageOffsets = {36, 55, 72};
	final TextLayout layout = new TextLayout(display);
	layout.setText(text);
	for (int i = 0; i < images.length; i++) {
		Rectangle bounds = images[i].getBounds();
		TextStyle imageStyle = new TextStyle(null, null, null);
		imageStyle.metrics = new GlyphMetrics(bounds.height, 0, bounds.width); 
		layout.setStyle(imageStyle, imageOffsets[i], imageOffsets[i]);
	}
	Rectangle bounds = button.getBounds();
	TextStyle buttonStyle = new TextStyle(null, null, null);
	buttonStyle.metrics = new GlyphMetrics(bounds.height, 0, bounds.width); 
	final int buttonOffset = text.length() - 2;
	layout.setStyle(buttonStyle, buttonOffset, buttonOffset);
	
	shell.addListener(SWT.Paint, new Listener() {
		public void handleEvent(Event event) {
			GC gc = event.gc;
			Point margin = new Point(10, 10);
			layout.setWidth(shell.getClientArea().width - 2 * margin.x);
			layout.draw(event.gc, margin.x, margin.y);
			for (int i = 0; i < images.length; i++) {
				int offset = imageOffsets[i];
				int lineIndex = layout.getLineIndex(offset);
				FontMetrics lineMetrics = layout.getLineMetrics(lineIndex);
				Point point = layout.getLocation(offset, false);
				GlyphMetrics glyphMetrics = layout.getStyle(offset).metrics;
				gc.drawImage(images[i], point.x + margin.x, point.y + margin.y + lineMetrics.getAscent() - glyphMetrics.ascent);
			}
			int lineIndex = layout.getLineIndex(buttonOffset);
			FontMetrics lineMetrics = layout.getLineMetrics(lineIndex);
			Point point = layout.getLocation(buttonOffset, false);
			GlyphMetrics glyphMetrics = layout.getStyle(buttonOffset).metrics;
			button.setLocation(point.x + margin.x, point.y + margin.y + lineMetrics.getAscent() - glyphMetrics.ascent);
		}
	});

	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	layout.dispose();
	for (int i = 0; i < images.length; i++) {
		images[i].dispose();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10119, 1, '[SWT]SnippetSnippet206.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Button example snippet: a Button with text and image
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */ 
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet206 {

public static void main(String[] args) {
	Display display = new Display();
	Image image = display.getSystemImage(SWT.ICON_QUESTION);
	Shell shell = new Shell(display);
	shell.setLayout (new GridLayout());
	Button button = new Button(shell, SWT.PUSH);
	button.setImage(image);
	button.setText("Button");
	shell.setSize(300, 300);
	shell.open();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10120, 1, '[SWT]SnippetSnippet207.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Use transformation matrices to reflect, rotate and shear images
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet207 {	
	public static void main(String[] args) {
		final Display display = new Display();
		
		final Image image = new Image(display, 110, 60);
		GC gc = new GC(image);
		Font font = new Font(display, "Times", 30, SWT.BOLD);
		gc.setFont(font);
		gc.setBackground(display.getSystemColor(SWT.COLOR_RED));
		gc.fillRectangle(0, 0, 110, 60);
		gc.setForeground(display.getSystemColor(SWT.COLOR_WHITE));
		gc.drawText("SWT", 10, 10, true);
		font.dispose();
		gc.dispose();
		
		final Rectangle rect = image.getBounds();
		Shell shell = new Shell(display);
		shell.setText("Matrix Tranformations");
		shell.setLayout(new FillLayout());
		final Canvas canvas = new Canvas(shell, SWT.DOUBLE_BUFFERED);
		canvas.addPaintListener(new PaintListener () {
			public void paintControl(PaintEvent e) {	
				GC gc = e.gc;
				gc.setAdvanced(true);
				if (!gc.getAdvanced()){
					gc.drawText("Advanced graphics not supported", 30, 30, true);
					return;
				}
				
				// Original image
				int x = 30, y = 30;
				gc.drawImage(image, x, y); 
				x += rect.width + 30;
				
				Transform transform = new Transform(display);
				
				// Note that the tranform is applied to the whole GC therefore
				// the coordinates need to be adjusted too.
				
				// Reflect around the y axis.
				transform.setElements(-1, 0, 0, 1, 0 ,0);
				gc.setTransform(transform);
				gc.drawImage(image, -1*x-rect.width, y);
				
				x = 30; y += rect.height + 30;
				
				// Reflect around the x axis. 
				transform.setElements(1, 0, 0, -1, 0, 0);
				gc.setTransform(transform);
				gc.drawImage(image, x, -1*y-rect.height);
				
				x += rect.width + 30;
				
				// Reflect around the x and y axes	
				transform.setElements(-1, 0, 0, -1, 0, 0);
				gc.setTransform(transform);
				gc.drawImage(image, -1*x-rect.width, -1*y-rect.height);
				
				x = 30; y += rect.height + 30;
				
				// Shear in the x-direction
				transform.setElements(1, 0, -1, 1, 0, 0);
				gc.setTransform(transform);
				gc.drawImage(image, 300, y);
				
				// Shear in y-direction
				transform.setElements(1, -1, 0, 1, 0, 0);
				gc.setTransform(transform);
				gc.drawImage(image, 150, 475);
				
				// Rotate by 45 degrees	
				float cos45 = (float)Math.cos(45);
				float sin45 = (float)Math.sin(45);
				transform.setElements(cos45, sin45, -sin45, cos45, 0, 0);
				gc.setTransform(transform);
				gc.drawImage(image, 350, 100);
				
				transform.dispose();
			}
		});
		
		shell.setSize(350, 550);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		image.dispose();
		display.dispose();
	}
}
	



', now(), now());
insert into SNIPPET values (10121, 1, '[SWT]SnippetSnippet208.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Change hue, saturation and brightness of a color
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet208 {	 

public static void main (String [] args) {
	PaletteData palette = new PaletteData(0xff, 0xff00, 0xff0000);	
	
	// ImageData showing variations of hue	
	ImageData hueData = new ImageData(360, 100, 24, palette);	
	float hue = 0;
	for (int x = 0; x < hueData.width; x++) {
		for (int y = 0; y < hueData.height; y++) {
			int pixel = palette.getPixel(new RGB(hue, 1f, 1f));
			hueData.setPixel(x, y, pixel);
		}
		hue += 360f / hueData.width;
	}

	// ImageData showing saturation on x axis and brightness on y axis
	ImageData saturationBrightnessData = new ImageData(360, 360, 24, palette);
	float saturation = 0f;
	float brightness = 1f;
	for (int x = 0; x < saturationBrightnessData.width; x++) {
		brightness = 1f;
		for (int y = 0; y < saturationBrightnessData.height; y++) {
			int pixel = palette.getPixel(new RGB(360f, saturation, brightness));	
			saturationBrightnessData.setPixel(x, y, pixel);
			brightness -= 1f / saturationBrightnessData.height;
		}
		saturation += 1f / saturationBrightnessData.width;
	}
		
	Display display = new Display();
	Image hueImage = new Image(display, hueData);
	Image saturationImage = new Image(display, saturationBrightnessData);
	Shell shell = new Shell(display);
	shell.setText("Hue, Saturation, Brightness");
	GridLayout gridLayout = new GridLayout(2, false);
	gridLayout.verticalSpacing = 10;
	gridLayout.marginWidth = gridLayout.marginHeight = 16;
	shell.setLayout(gridLayout);		
	
	Label label = new Label(shell, SWT.CENTER);
	label.setImage(hueImage);
	GridData data = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1);
	label.setLayoutData(data);	
	
	label = new Label(shell, SWT.CENTER); //spacer
	label = new Label(shell, SWT.CENTER);
	label.setText("Hue");
	data = new GridData(SWT.CENTER, SWT.CENTER, false, false);
	label.setLayoutData(data);
	label = new Label(shell, SWT.CENTER); //spacer
	data = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 1);
	label.setLayoutData(data);
	
	label = new Label(shell, SWT.LEFT);
	label.setText("Brightness");
	data = new GridData(SWT.LEFT, SWT.CENTER, false, false);
	label.setLayoutData(data);
	
	label = new Label(shell, SWT.CENTER);
	label.setImage(saturationImage);
	data = new GridData(SWT.CENTER, SWT.CENTER, false, false);
	label.setLayoutData (data);
	
	label = new Label(shell, SWT.CENTER); //spacer
	label = new Label(shell, SWT.CENTER);
	label.setText("Saturation");
	data = new GridData(SWT.CENTER, SWT.CENTER, false, false);
	label.setLayoutData(data);
	
	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) {
			display.sleep();
		}
	}
	hueImage.dispose();
	saturationImage.dispose();	
	display.dispose();
}

}


', now(), now());
insert into SNIPPET values (10122, 1, '[SWT]SnippetSnippet209.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * SWT OpenGL snippet: use JOGL to draw to an SWT GLCanvas
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;

import javax.media.opengl.GL;
import javax.media.opengl.GLContext;
import javax.media.opengl.GLDrawableFactory;
import javax.media.opengl.glu.GLU;

public class Snippet209 {
	static void drawTorus(GL gl, float r, float R, int nsides, int rings) {
		float ringDelta = 2.0f * (float) Math.PI / rings;
		float sideDelta = 2.0f * (float) Math.PI / nsides;
		float theta = 0.0f, cosTheta = 1.0f, sinTheta = 0.0f;
		for (int i = rings - 1; i >= 0; i--) {
			float theta1 = theta + ringDelta;
			float cosTheta1 = (float) Math.cos(theta1);
			float sinTheta1 = (float) Math.sin(theta1);
			gl.glBegin(GL.GL_QUAD_STRIP);
			float phi = 0.0f;
			for (int j = nsides; j >= 0; j--) {
				phi += sideDelta;
				float cosPhi = (float) Math.cos(phi);
				float sinPhi = (float) Math.sin(phi);
				float dist = R + r * cosPhi;
				gl.glNormal3f(cosTheta1 * cosPhi, -sinTheta1 * cosPhi, sinPhi);
				gl.glVertex3f(cosTheta1 * dist, -sinTheta1 * dist, r * sinPhi);
				gl.glNormal3f(cosTheta * cosPhi, -sinTheta * cosPhi, sinPhi);
				gl.glVertex3f(cosTheta * dist, -sinTheta * dist, r * sinPhi);
			}
			gl.glEnd();
			theta = theta1;
			cosTheta = cosTheta1;
			sinTheta = sinTheta1;
		}
	}

	public static void main(String [] args) {
		final Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		Composite comp = new Composite(shell, SWT.NONE);
		comp.setLayout(new FillLayout());
		GLData data = new GLData ();
		data.doubleBuffer = true;
		final GLCanvas canvas = new GLCanvas(comp, SWT.NONE, data);

		canvas.setCurrent();
		final GLContext context = GLDrawableFactory.getFactory().createExternalGLContext();

		canvas.addListener(SWT.Resize, new Listener() {
			public void handleEvent(Event event) {
				Rectangle bounds = canvas.getBounds();
				float fAspect = (float) bounds.width / (float) bounds.height;
				canvas.setCurrent();
				context.makeCurrent();
				GL gl = context.getGL ();
				gl.glViewport(0, 0, bounds.width, bounds.height);
				gl.glMatrixMode(GL.GL_PROJECTION);
				gl.glLoadIdentity();
				GLU glu = new GLU();
				glu.gluPerspective(45.0f, fAspect, 0.5f, 400.0f);
				gl.glMatrixMode(GL.GL_MODELVIEW);
				gl.glLoadIdentity();
				context.release();
			}
		});

		context.makeCurrent();
		GL gl = context.getGL ();
		gl.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
		gl.glColor3f(1.0f, 0.0f, 0.0f);
		gl.glHint(GL.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
		gl.glClearDepth(1.0);
		gl.glLineWidth(2);
		gl.glEnable(GL.GL_DEPTH_TEST);
		context.release();

		shell.setText("SWT/JOGL Example");
		shell.setSize(640, 480);
		shell.open();

		display.asyncExec(new Runnable() {
			int rot = 0;
			public void run() {
				if (!canvas.isDisposed()) {
					canvas.setCurrent();
					context.makeCurrent();
					GL gl = context.getGL ();
					gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
					gl.glClearColor(.3f, .5f, .8f, 1.0f);
					gl.glLoadIdentity();
					gl.glTranslatef(0.0f, 0.0f, -10.0f);
					float frot = rot;
					gl.glRotatef(0.15f * rot, 2.0f * frot, 10.0f * frot, 1.0f);
					gl.glRotatef(0.3f * rot, 3.0f * frot, 1.0f * frot, 1.0f);
					rot++;
					gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL.GL_LINE);
					gl.glColor3f(0.9f, 0.9f, 0.9f);
					drawTorus(gl, 1, 1.9f + ((float) Math.sin((0.004f * frot))), 15, 15);
					canvas.swapBuffers();
					context.release();
					display.asyncExec(this);
				}
			}
		});

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10123, 1, '[SWT]SnippetSnippet21.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Canvas example snippet: implement tab traversal (behave like a tab group)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet21 {

public static void main (String [] args) {
	Display display = new Display ();
	final Color red = display.getSystemColor (SWT.COLOR_RED);
	final Color blue = display.getSystemColor (SWT.COLOR_BLUE);
	Shell shell = new Shell (display);
	Button b = new Button (shell, SWT.PUSH);
	b.setBounds (10, 10, 100, 32);
	b.setText ("Button");
	shell.setDefaultButton (b);
	final Canvas c = new Canvas (shell, SWT.BORDER);
	c.setBounds (10, 50, 100, 32);
	c.addListener (SWT.Traverse, new Listener () {
		public void handleEvent (Event e) {
			switch (e.detail) {
				/* Do tab group traversal */
				case SWT.TRAVERSE_ESCAPE:
				case SWT.TRAVERSE_RETURN:
				case SWT.TRAVERSE_TAB_NEXT:	
				case SWT.TRAVERSE_TAB_PREVIOUS:
				case SWT.TRAVERSE_PAGE_NEXT:	
				case SWT.TRAVERSE_PAGE_PREVIOUS:
					e.doit = true;
					break;
			}
		}
	});
	c.addListener (SWT.FocusIn, new Listener () {
		public void handleEvent (Event e) {
			c.setBackground (red);
		}
	});
	c.addListener (SWT.FocusOut, new Listener () {
		public void handleEvent (Event e) {
			c.setBackground (blue);
		}
	});
	c.addListener (SWT.KeyDown, new Listener () {
		public void handleEvent (Event e) {
			System.out.println ("KEY");
			for (int i=0; i<64; i++) {
				Color c1 = red, c2 = blue;
				if (c.isFocusControl ()) {
					c1 = blue;  c2 = red;
				}
				c.setBackground (c1);
				c.update ();
				c.setBackground (c2);
			}
		}
	});
	Text t = new Text (shell, SWT.SINGLE | SWT.BORDER);
	t.setBounds (10, 85, 100, 32);

	Text r = new Text (shell, SWT.MULTI | SWT.BORDER);
	r.setBounds (10, 120, 100, 32);
	
	c.setFocus ();
	shell.setSize (200, 200);
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10124, 1, '[SWT]SnippetSnippet210.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Drag text between two StyledText widgets
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */ 
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet210 {
	static String string1 = "A drag source is the provider of data in a Drag and Drop data transfer as well as "+
                           "the originator of the Drag and Drop operation. The data provided by the drag source "+
                           "may be transferred to another location in the same widget, to a different widget "+
                           "within the same application, or to a different application altogether. For example, "+
                           "you can drag text from your application and drop it on an email application, or you "+
                           "could drag an item in a tree and drop it below a different node in the same tree.";

	static String string2 = "A drop target receives data in a Drag and Drop operation. The data received by "+
	                        "the drop target may have come from the same widget, from a different widget within "+
	                        "the same application, or from a different application altogether. For example, you "+
	                        "can drag text from an email application and drop it on your application, or you could "+
	                        "drag an item in a tree and drop it below a different node in the same tree.";
	
public static void main (String [] args) {
	final Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	int style = SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
	final StyledText text1 = new StyledText(shell, style);
	text1.setText(string1);
	DragSource source = new DragSource(text1, DND.DROP_COPY | DND.DROP_MOVE);
	source.setTransfer(new Transfer[] {TextTransfer.getInstance()});
	source.addDragListener(new DragSourceAdapter() {
		Point selection;
		public void dragStart(DragSourceEvent e) {
			selection = text1.getSelection();
			e.doit = selection.x != selection.y;
		}
		public void dragSetData(DragSourceEvent e) {
			e.data = text1.getText(selection.x, selection.y-1);
		}
		public void dragFinished(DragSourceEvent e) {
			if (e.detail == DND.DROP_MOVE) {
				text1.replaceTextRange(selection.x, selection.y - selection.x, "");
			}
			selection = null;
		}
	});
	
	final StyledText text2 = new StyledText(shell, style);
	text2.setText(string2);
	DropTarget target = new DropTarget(text2, DND.DROP_DEFAULT | DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK);
	target.setTransfer(new Transfer[] {TextTransfer.getInstance()});
	target.addDropListener(new DropTargetAdapter() {
		public void dragEnter(DropTargetEvent e) {
			if (e.detail == DND.DROP_DEFAULT)
				e.detail = DND.DROP_COPY;
		}
		public void dragOperationChanged(DropTargetEvent e) {
			if (e.detail == DND.DROP_DEFAULT)
				e.detail = DND.DROP_COPY;
		}
		public void drop(DropTargetEvent e) {
			text2.insert((String)e.data);
		}
	});
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10125, 1, '[SWT]SnippetSnippet211.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * SWT StyledText snippet: use rise and font with StyleRange. 
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.custom.*;

public class Snippet211 {

	static String text = 
		"You can set any font you want in a range. You can also set a baseline rise and all other old features" + 
		" like background and foreground, and mix them any way you want. Totally awesome.";
	
	public static void main(String [] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		StyledText styledText = new StyledText(shell, SWT.WRAP | SWT.BORDER);
		styledText.setText(text);
		FontData data = styledText.getFont().getFontData()[0];
		Font font1 = new Font(display, data.getName(), data.getHeight() * 2, data.getStyle());
		Font font2 = new Font(display, data.getName(), data.getHeight() * 4 / 5, data.getStyle());
		StyleRange[] styles = new StyleRange[8];
		styles[0] = new StyleRange();
		styles[0].font = font1; 
		styles[1] = new StyleRange();
		styles[1].rise = data.getHeight() / 3; 
		styles[2] = new StyleRange();
		styles[2].background = display.getSystemColor(SWT.COLOR_GREEN); 
		styles[3] = new StyleRange();
		styles[3].foreground = display.getSystemColor(SWT.COLOR_MAGENTA); 
		styles[4] = new StyleRange();
		styles[4].font = font2; 
		styles[4].foreground = display.getSystemColor(SWT.COLOR_BLUE);;
		styles[4].underline = true;
		styles[5] = new StyleRange();
		styles[5].rise = -data.getHeight() / 3; 
		styles[5].strikeout = true;
		styles[5].underline = true;
		styles[6] = new StyleRange();
		styles[6].font = font1; 
		styles[6].foreground = display.getSystemColor(SWT.COLOR_YELLOW);
		styles[6].background = display.getSystemColor(SWT.COLOR_BLUE);
		styles[7] = new StyleRange();
		styles[7].rise =  data.getHeight() / 3;
		styles[7].underline = true;
		styles[7].fontStyle = SWT.BOLD;
		styles[7].foreground = display.getSystemColor(SWT.COLOR_RED);
		styles[7].background = display.getSystemColor(SWT.COLOR_BLACK);
		
		int[] ranges = new int[] {16, 4, 61, 13, 107, 10, 122, 10, 134, 3, 143, 6, 160, 7, 168, 7};
		styledText.setStyleRanges(ranges, styles);
		
		shell.setSize(300, 300);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		font1.dispose();
		font2.dispose();		
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10126, 1, '[SWT]SnippetSnippet212.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.custom.*;

/**
 * StyledText snippet: embed images
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
public class Snippet212 {
	
	static StyledText styledText;
	static String text = 
		"This snippet shows how to embed images in a StyledText.\n"+
		"Here is one: \uFFFC, and here is another: \uFFFC."+
		"Use the add button to add an image from your filesystem to the StyledText at the current caret offset.";
	static Image[] images;
	static int[] offsets;

	static void addImage(Image image, int offset) {
		StyleRange style = new StyleRange ();
		style.start = offset;
		style.length = 1;
		Rectangle rect = image.getBounds();
		style.metrics = new GlyphMetrics(rect.height, 0, rect.width);
		styledText.setStyleRange(style);		
	}
	
	public static void main(String [] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		styledText = new StyledText(shell, SWT.WRAP | SWT.BORDER);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		styledText.setText(text);
		images = new Image[] {
			display.getSystemImage(SWT.ICON_QUESTION),
			display.getSystemImage(SWT.ICON_INFORMATION),
		};
		offsets = new int[images.length];
		int lastOffset = 0;
		for (int i = 0; i < images.length; i++) {
			int offset = text.indexOf("\uFFFC", lastOffset);
			offsets[i] = offset;
			addImage(images[i], offset);
			lastOffset = offset + 1;
		}
		
		// use a verify listener to keep the offsets up to date
		styledText.addVerifyListener(new VerifyListener()  {
			public void verifyText(VerifyEvent e) {
				int start = e.start;
				int replaceCharCount = e.end - e.start;
				int newCharCount = e.text.length();
				for (int i = 0; i < offsets.length; i++) {
					int offset = offsets[i];
					if (start <= offset && offset < start + replaceCharCount) {
						// this image is being deleted from the text
						if (images[i] != null && !images[i].isDisposed()) {
							images[i].dispose();
							images[i] = null;
						}
						offset = -1;
					}
					if (offset != -1 && offset >= start) offset += newCharCount - replaceCharCount;
					offsets[i] = offset;
				}
			}
		});
		styledText.addPaintObjectListener(new PaintObjectListener() {
			public void paintObject(PaintObjectEvent event) {
				GC gc = event.gc;
				StyleRange style = event.style;
				int start = style.start;
				for (int i = 0; i < offsets.length; i++) {
					int offset = offsets[i];
					if (start == offset) {
						Image image = images[i];
						int x = event.x;
						int y = event.y + event.ascent - style.metrics.ascent;						
						gc.drawImage(image, x, y);
					}
				}
			}
		});
		
		Button button = new Button (shell, SWT.PUSH);
		button.setText("Add Image");
		button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				FileDialog dialog = new FileDialog(shell);
				String filename = dialog.open();
				if (filename != null) {
					try {
						Image image = new Image(display, filename);
						int offset = styledText.getCaretOffset();
						styledText.replaceTextRange(offset, 0, "\uFFFC");
						int index = 0;
						while (index < offsets.length) {
							if (offsets[index] == -1 && images[index] == null) break;
							index++;
						}
						if (index == offsets.length) {
							int[] tmpOffsets = new int[index + 1];
							System.arraycopy(offsets, 0, tmpOffsets, 0, offsets.length);
							offsets = tmpOffsets;
							Image[] tmpImages = new Image[index + 1];
							System.arraycopy(images, 0, tmpImages, 0, images.length);
							images = tmpImages;
						}
						offsets[index] = offset;
						images[index] = image;
						addImage(image, offset);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}				
			}
		});
		shell.setSize(400, 400);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		for (int i = 0; i < images.length; i++) {
			Image image = images[i];
			if (image != null && !image.isDisposed()) {
				image.dispose();
			}
		}
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10127, 1, '[SWT]SnippetSnippet213.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * SWT StyledText snippet: use indent, alignment and justify.
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.custom.*;

public class Snippet213 {

	static String text = 
		"The first paragraph has an indentation of fifty pixels. Indentation is the amount of white space in front of the first line of a paragraph. If this paragraph wraps to several lines you should see the indentation only on the first line.\n\n" +
		"The second paragraph is center aligned. Alignment only works when the StyledText is using word wrap. Alignment, as with all other line attributes, can be set for the whole widget or just for a set of lines.\n\n" +
		"The third paragraph is justified. Like alignment, justify only works when the StyledText is using word wrap. If the paragraph wraps to several lines, the justification is performed on all lines but the last one.\n\n" +		
		"The last paragraph is justified and right aligned. In this case, the alignment is only noticeable in the final line.";
	
	public static void main(String [] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		StyledText styledText = new StyledText(shell, SWT.WRAP | SWT.BORDER);
		styledText.setText(text);
		styledText.setLineIndent(0, 1, 50);
		styledText.setLineAlignment(2, 1, SWT.CENTER);
		styledText.setLineJustify(4, 1, true);
		styledText.setLineAlignment(6, 1, SWT.RIGHT);
		styledText.setLineJustify(6, 1, true);
		
		shell.setSize(300, 400);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10128, 1, '[SWT]SnippetSnippet214.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Control example snippet: set a background image (a dynamic gradient)
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet214 {
	static Image oldImage;
	public static void main(String [] args) {
		final Display display = new Display ();
		final Shell shell = new Shell (display);
		shell.setBackgroundMode (SWT.INHERIT_DEFAULT);
		FillLayout layout1 = new FillLayout (SWT.VERTICAL);
		layout1.marginWidth = layout1.marginHeight = 10;
		shell.setLayout (layout1);
		Group group = new Group (shell, SWT.NONE);
		group.setText ("Group ");
		RowLayout layout2 = new RowLayout (SWT.VERTICAL);
		layout2.marginWidth = layout2.marginHeight = layout2.spacing = 10;
		group.setLayout (layout2);
		for (int i=0; i<8; i++) {
			Button button = new Button (group, SWT.RADIO);
			button.setText ("Button " + i);
		}
		shell.addListener (SWT.Resize, new Listener () {
			public void handleEvent (Event event) {
				Rectangle rect = shell.getClientArea ();
				Image newImage = new Image (display, Math.max (1, rect.width), 1);	
				GC gc = new GC (newImage);
				gc.setForeground (display.getSystemColor (SWT.COLOR_WHITE));
				gc.setBackground (display.getSystemColor (SWT.COLOR_BLUE));
				gc.fillGradientRectangle (rect.x, rect.y, rect.width, 1, false);
				gc.dispose ();
				shell.setBackgroundImage (newImage);
				if (oldImage != null) oldImage.dispose ();
				oldImage = newImage;
			}
		});
		shell.pack ();
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		if (oldImage != null) oldImage.dispose ();
		display.dispose ();
	}
}
', now(), now());
insert into SNIPPET values (10129, 1, '[SWT]SnippetSnippet215.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * GC example snippet: take a screen shot with a GC
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.custom.*;

public class Snippet215 {

public static void main(String[] args) {
	final Display display = new Display();
	final Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	Button button = new Button(shell, SWT.PUSH);
	button.setText("Capture");
	button.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event event) {
			
			/* Take the screen shot */
			GC gc = new GC(display);
			final Image image = new Image(display, display.getBounds());
			gc.copyArea(image, 0, 0);
			gc.dispose();
			
			Shell popup = new Shell(shell, SWT.SHELL_TRIM);
			popup.setLayout(new FillLayout());
			popup.setText("Image");
			popup.setBounds(50, 50, 200, 200);
			popup.addListener(SWT.Close, new Listener() {
				public void handleEvent(Event e) {
					image.dispose();
				}
			});
			
			ScrolledComposite sc = new ScrolledComposite (popup, SWT.V_SCROLL | SWT.H_SCROLL);
			Canvas canvas = new Canvas(sc, SWT.NONE);
			sc.setContent(canvas);
			canvas.setBounds(display.getBounds ());
			canvas.addPaintListener(new PaintListener() {
				public void paintControl(PaintEvent e) {
					e.gc.drawImage(image, 0, 0);
				}
			});
			popup.open();
		}
	});
	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10130, 1, '[SWT]SnippetSnippet216.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tool Tips example snippet: show a tool tip inside a rectangle
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet216 {
	public static void main (String [] args) {
		Display display = new Display ();
		final Color [] colors = {
			display.getSystemColor (SWT.COLOR_RED),
			display.getSystemColor (SWT.COLOR_GREEN),
			display.getSystemColor (SWT.COLOR_BLUE),
		};
		final Rectangle [] rects = {
			new Rectangle (10, 10, 30, 30),
			new Rectangle (20, 45, 25, 35),
			new Rectangle (80, 80, 10, 10),
		};
		final Shell shell = new Shell (display);
		Listener mouseListener = new Listener () {
			public void handleEvent (Event event) {
				switch (event.type) {
					case SWT.MouseEnter:
					case SWT.MouseMove:
						for (int i=0; i<rects.length; i++) {
							if (rects [i].contains (event.x, event.y)) {
								String text = "ToolTip " + i;
								if (!(text.equals (shell.getToolTipText ()))) {
									shell.setToolTipText ("ToolTip " + i);
								}
								return;
							}
						}
						shell.setToolTipText (null);
						break;
					}
			}
		};
		shell.addListener (SWT.MouseMove, mouseListener);
		shell.addListener (SWT.MouseEnter, mouseListener);
		shell.addListener (SWT.Paint, new Listener () {
			public void handleEvent (Event event) {
				GC gc = event.gc;
				for (int i=0; i<rects.length; i++) {
					gc.setBackground (colors [i]);
					gc.fillRectangle (rects [i]);
					gc.drawRectangle (rects [i]);
				}
			}
		});
		shell.setSize (200, 200);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
}
', now(), now());
insert into SNIPPET values (10131, 1, '[SWT]SnippetSnippet217.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/**
 * StyledText snippet: embed controls
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
public class Snippet217 {
	
	static StyledText styledText;
	static String text = 
		"This snippet shows how to embed widgets in a StyledText.\n"+
		"Here is one: \uFFFC, and here is another: \uFFFC.";
	static int[] offsets;
	static Control[] controls;
	static int MARGIN = 5;
	
	static void addControl(Control control, int offset) {
		StyleRange style = new StyleRange ();
		style.start = offset;
		style.length = 1;
		control.pack();
		Rectangle rect = control.getBounds();
		int ascent = 2*rect.height/3;
		int descent = rect.height - ascent;
		style.metrics = new GlyphMetrics(ascent + MARGIN, descent + MARGIN, rect.width + 2*MARGIN);
		styledText.setStyleRange(style);	
	}
	
	public static void main(String [] args) {
		final Display display = new Display();
		Font font = new Font(display, "Tahoma", 32, SWT.NORMAL);
		final Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		styledText = new StyledText(shell, SWT.WRAP | SWT.BORDER);
		styledText.setFont(font);
		styledText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		styledText.setText(text);
		controls = new Control[2];
		Button button = new Button(styledText, SWT.PUSH);
		button.setText("Button 1");
		controls[0] = button;
		Combo combo = new Combo(styledText, SWT.NONE);
		combo.add("item 1");
		combo.add("another item");
		controls[1] = combo;
		offsets = new int[controls.length];
		int lastOffset = 0;
		for (int i = 0; i < controls.length; i++) {
			int offset = text.indexOf("\uFFFC", lastOffset);
			offsets[i] = offset;
			addControl(controls[i], offsets[i]);
			lastOffset = offset + 1;
		}
		
		// use a verify listener to keep the offsets up to date
		styledText.addVerifyListener(new VerifyListener()  {
			public void verifyText(VerifyEvent e) {
				int start = e.start;
				int replaceCharCount = e.end - e.start;
				int newCharCount = e.text.length();
				for (int i = 0; i < offsets.length; i++) {
					int offset = offsets[i];
					if (start <= offset && offset < start + replaceCharCount) {
						// this widget is being deleted from the text
						if (controls[i] != null && !controls[i].isDisposed()) {
							controls[i].dispose();
							controls[i] = null;
						}
						offset = -1;
					}
					if (offset != -1 && offset >= start) offset += newCharCount - replaceCharCount;
					offsets[i] = offset;
				}
			}
		});
		
		// reposition widgets on paint event
		styledText.addPaintObjectListener(new PaintObjectListener() {
			public void paintObject(PaintObjectEvent event) {
				StyleRange style = event.style;
				int start = style.start;
				for (int i = 0; i < offsets.length; i++) {
					int offset = offsets[i];
					if (start == offset) {
						Point pt = controls[i].getSize();
						int x = event.x + MARGIN;
						int y = event.y + event.ascent - 2*pt.y/3;
						controls[i].setLocation(x, y);
						break;
					}
				}
			}
		});
			
		shell.setSize(400, 400);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		font.dispose();
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10132, 1, '[SWT]SnippetSnippet218.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * SWT StyledText snippet: use gradient background.
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.*;

public class Snippet218 {

	static String text = "Plans do not materialize out of nowhere, nor are they entirely static. To ensure the planning process is " +
		"transparent and open to the entire Eclipse community, we (the Eclipse PMC) post plans in an embryonic "+
		"form and revise them throughout the release cycle. \n"+
		"The first part of the plan deals with the important matters of release deliverables, release milestones, target "+
		"operating environments, and release-to-release compatibility. These are all things that need to be clear for "+
		"any release, even if no features were to change.  \n";
	static Image oldImage;
	
	public static void main(String [] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		final StyledText styledText = new StyledText(shell, SWT.WRAP | SWT.BORDER);
		styledText.setText(text);
		FontData data = display.getSystemFont().getFontData()[0];
		Font font = new Font(display, data.getName(), 16, SWT.BOLD);
		styledText.setFont(font);
		styledText.setForeground(display.getSystemColor (SWT.COLOR_BLUE));
		styledText.addListener (SWT.Resize, new Listener () {
			public void handleEvent (Event event) {
				Rectangle rect = styledText.getClientArea ();
				Image newImage = new Image (display, 1, Math.max (1, rect.height));
				GC gc = new GC (newImage);
				gc.setForeground (display.getSystemColor (SWT.COLOR_WHITE));
				gc.setBackground (display.getSystemColor (SWT.COLOR_YELLOW));
				gc.fillGradientRectangle (rect.x, rect.y, 1, rect.height, true);
				gc.dispose ();
				styledText.setBackgroundImage (newImage);
				if (oldImage != null) oldImage.dispose ();
				oldImage = newImage;
			}
		});	
		shell.setSize(700, 400);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		if (oldImage != null) oldImage.dispose ();
		font.dispose();
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10133, 1, '[SWT]SnippetSnippet219.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

/**
 * Region snippet: Create non-rectangular shell from an image with transparency
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
public class Snippet219 {
	public static void main(String[] args) {
		final Display display = new Display ();
		final Image image = display.getSystemImage(SWT.ICON_INFORMATION);
		final Shell shell = new Shell (display, SWT.NO_TRIM);
		Region region = new Region();
		final ImageData imageData = image.getImageData();
		if (imageData.alphaData != null) {
			Rectangle pixel = new Rectangle(0, 0, 1, 1);
			for (int y = 0; y < imageData.height; y++) {
				for (int x = 0; x < imageData.width; x++) {
					if (imageData.getAlpha(x, y) == 255) {
						pixel.x = imageData.x + x;
						pixel.y = imageData.y + y;
						region.add(pixel);
					} 
				}
			}
		} else {
			ImageData mask = imageData.getTransparencyMask();
			Rectangle pixel = new Rectangle(0, 0, 1, 1);
			for (int y = 0; y < mask.height; y++) {
				for (int x = 0; x < mask.width; x++) {
					if (mask.getPixel(x, y) != 0) {
						pixel.x = imageData.x + x;
						pixel.y = imageData.y + y;
						region.add(pixel);
					}
				}
			}
		}
		shell.setRegion(region);

		Listener l = new Listener() {
			int startX, startY;
			public void handleEvent(Event e)  {
				if (e.type == SWT.KeyDown && e.character == SWT.ESC) {
					shell.dispose();
				}
				if (e.type == SWT.MouseDown && e.button == 1) {
					startX = e.x;
					startY = e.y; 
				}
				if (e.type == SWT.MouseMove && (e.stateMask & SWT.BUTTON1) != 0) {
					Point p = shell.toDisplay(e.x, e.y);
					p.x -= startX;
					p.y -= startY;
					shell.setLocation(p);
				}
				if (e.type == SWT.Paint) {
					e.gc.drawImage(image, imageData.x, imageData.y);
				}
			}
		};
		shell.addListener(SWT.KeyDown, l);
		shell.addListener(SWT.MouseDown, l);
		shell.addListener(SWT.MouseMove, l);
		shell.addListener(SWT.Paint, l);

		shell.setSize(imageData.x + imageData.width, imageData.y + imageData.height);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		region.dispose();
		image.dispose ();
		display.dispose ();
	}
}
', now(), now());
insert into SNIPPET values (10134, 1, '[SWT]SnippetSnippet22.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Text example snippet: select all the text in the control
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.widgets.*;

public class Snippet22 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Text text = new Text (shell, 0);
	text.setText ("ASDF");
	text.setSize (64, 32);
	text.selectAll ();
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10135, 1, '[SWT]SnippetSnippet220.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
/* 
 * Tree example snippet: Images on the right side of the TreeItem
 *
 * For a detailed explanation of this snippet see
 * http://www.eclipse.org/articles/Article-CustomDrawingTableAndTreeItems/customDraw.htm#_example5
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet220 {	
	
public static void main(String [] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setBounds(10, 10, 350, 200);
	Image xImage = new Image (display, 16, 16);
	GC gc = new GC(xImage);
	gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
	gc.drawLine(1, 1, 14, 14);
	gc.drawLine(1, 14, 14, 1);
	gc.drawOval(2, 2, 11, 11);
	gc.dispose();
	final int IMAGE_MARGIN = 2;
	final Tree tree = new Tree(shell, SWT.CHECK);
	tree.setBounds(10, 10, 300, 150);
	TreeItem item = new TreeItem(tree, SWT.NONE);
	item.setText("root item");
	for (int i = 0; i < 4; i++) {
		TreeItem newItem = new TreeItem(item, SWT.NONE);
		newItem.setText("descendent " + i);
		if (i % 2 == 0) newItem.setData(xImage);
		item.setExpanded(true);
		item = newItem;
	}

	/*
	 * NOTE: MeasureItem and PaintItem are called repeatedly.  Therefore it is
	 * critical for performance that these methods be as efficient as possible.
	 */
	tree.addListener(SWT.MeasureItem, new Listener() {
		public void handleEvent(Event event) {
			TreeItem item = (TreeItem)event.item;
			Image trailingImage = (Image)item.getData();
			if (trailingImage != null) {
				event.width += trailingImage.getBounds().width + IMAGE_MARGIN;
			}
		}
	});
	tree.addListener(SWT.PaintItem, new Listener() {
		public void handleEvent(Event event) {
			TreeItem item = (TreeItem)event.item;
			Image trailingImage = (Image)item.getData();
			if (trailingImage != null) {
				int x = event.x + event.width + IMAGE_MARGIN;
				int itemHeight = tree.getItemHeight();
				int imageHeight = trailingImage.getBounds().height;
				int y = event.y + (itemHeight - imageHeight) / 2;
				event.gc.drawImage(trailingImage, x, y);
			}
		}
	});

	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	xImage.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10136, 1, '[SWT]SnippetSnippet221.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
/* 
 * example snippet: Scroll tree when mouse at top or bottom
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet221 {
	static Runnable Heartbeat;
	static boolean Tracking;
	static int ScrollSpeed = 40;
	
public static void main(String[] args) {
	final Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	final Tree tree = new Tree(shell, SWT.FULL_SELECTION | SWT.BORDER);
	tree.setHeaderVisible(true);
	TreeColumn column0 = new TreeColumn(tree, SWT.LEFT);
	column0.setText("Column 0");
	TreeColumn column1 = new TreeColumn(tree, SWT.LEFT);
	column1.setText("Column 1");
	TreeColumn column2 = new TreeColumn(tree, SWT.LEFT);
	column2.setText("Column 2");
	for (int i = 0; i < 9; i++) {
		TreeItem item = new TreeItem(tree, SWT.NONE);
		item.setText("item "+i);
		item.setText(1, "column 1 - "+i);
		item.setText(2, "column 2 - "+i);
		for (int j = 0; j < 9; j++) {
			TreeItem subItem = new TreeItem(item, SWT.NONE);
			subItem.setText("item "+i+" "+j);
			subItem.setText(1, "column 1 - "+i+" "+j);
			subItem.setText(2, "column 2 - "+i+" "+j);
			for (int k = 0; k < 9; k++) {
				TreeItem subsubItem = new TreeItem(subItem, SWT.NONE);
				subsubItem.setText("item "+i+" "+j+" "+k);
				subsubItem.setText(1, "column 1 - "+i+" "+j+" "+k);
				subsubItem.setText(2, "column 2 - "+i+" "+j+" "+k);
			}
		}
	}
	column0.pack();
	column1.pack();
	column2.pack();
	
	Heartbeat = new Runnable() {
		public void run() {
			if (!Tracking || tree.isDisposed()) return;
			Point cursor = display.getCursorLocation();
			cursor = display.map(null, tree, cursor);
			Scroll(tree, cursor.x, cursor.y);
			display.timerExec(ScrollSpeed, Heartbeat);
		}
	};
	Listener listener = new Listener() {
		public void handleEvent(Event event) {
			switch (event.type) {
			case SWT.MouseEnter:
				Tracking = true;
				display.timerExec(0, Heartbeat);
				break;
			case SWT.MouseExit:
				Tracking = false;
				break;
			}
		}
	};
	tree.addListener(SWT.MouseEnter, listener); 
	tree.addListener(SWT.MouseExit, listener);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
static void Scroll(Tree tree, int x, int y) {
	TreeItem item = tree.getItem(new Point(x, y));
	if (item == null) return;
	Rectangle area = tree.getClientArea();
	int headerHeight = tree.getHeaderHeight();
	int itemHeight= tree.getItemHeight();
	TreeItem nextItem = null;
	if (y < area.y + headerHeight + 2 * itemHeight) {
		nextItem = PreviousItem(tree, item);
	}
	if (y > area.y + area.height - 2 * itemHeight) {
		nextItem = NextItem(tree, item);
	}
	if (nextItem != null) tree.showItem(nextItem);
}

static TreeItem PreviousItem(Tree tree, TreeItem item) {
	if (item == null) return null;
	TreeItem childItem = item;
	TreeItem parentItem = childItem.getParentItem();
	int index = parentItem == null ? tree.indexOf(childItem) : parentItem.indexOf(childItem);
	if (index == 0) {
		return parentItem;
	} else {
		TreeItem nextItem = parentItem == null ? tree.getItem(index-1) : parentItem.getItem(index-1);
		int count = nextItem.getItemCount();
		while (count > 0 && nextItem.getExpanded()) {
			nextItem = nextItem.getItem(count - 1);
			count = nextItem.getItemCount();
		}
		return nextItem;
	}
}
static TreeItem NextItem(Tree tree, TreeItem item) {
	if (item == null) return null;
	if (item.getExpanded()) {
		return item.getItem(0);
	} else {
		TreeItem childItem = item;
		TreeItem parentItem = childItem.getParentItem();
		int index = parentItem == null ? tree.indexOf(childItem) : parentItem.indexOf(childItem);
		int count = parentItem == null ? tree.getItemCount() : parentItem.getItemCount();
		while (true) {
			if (index + 1 < count) {
				return parentItem == null ? tree.getItem(index + 1) : parentItem.getItem(index + 1);
			} else {
				if (parentItem == null) {
					return null;
				} else {
					childItem = parentItem;
					parentItem = childItem.getParentItem();
					index = parentItem == null ? tree.indexOf(childItem) : parentItem.indexOf(childItem);
					count = parentItem == null ? tree.getItemCount() : parentItem.getItemCount();
				}
			}
		}
	}
}
}
', now(), now());
insert into SNIPPET values (10137, 1, '[SWT]SnippetSnippet222.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
/* 
 * example snippet: StyledText bulleted list example
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;

public class Snippet222 {
	
public static void main(String[] args) {	
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setText("StyledText Bullet Example");
	shell.setLayout(new FillLayout());
	final StyledText styledText = new StyledText (shell, SWT.FULL_SELECTION | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
	StringBuffer text = new StringBuffer();
	text.append("Here is StyledText with some bulleted lists:\n\n");
	for (int i = 0; i < 4; i++) text.append("Red Bullet List Item " + i + "\n");
	text.append("\n");
	for (int i = 0; i < 2; i++) text.append("Numbered List Item " + i + "\n");
	for (int i = 0; i < 4; i++) text.append("Sub List Item " + i + "\n");
	for (int i = 0; i < 2; i++) text.append("Numbered List Item " + (2 + i) + "\n");
	text.append("\n");
	for (int i = 0; i < 4; i++) text.append("Custom Draw List Item " + i + "\n");	
	styledText.setText(text.toString());
		
	StyleRange style0 = new StyleRange();
	style0.metrics = new GlyphMetrics(0, 0, 40);
	style0.foreground = display.getSystemColor(SWT.COLOR_RED);
	Bullet bullet0 = new Bullet (style0);
	StyleRange style1 = new StyleRange();
	style1.metrics = new GlyphMetrics(0, 0, 50);
	style1.foreground = display.getSystemColor(SWT.COLOR_BLUE);
	Bullet bullet1 = new Bullet (ST.BULLET_NUMBER | ST.BULLET_TEXT, style1);
	bullet1.text = ".";
	StyleRange style2 = new StyleRange();
	style2.metrics = new GlyphMetrics(0, 0, 80);
	style2.foreground = display.getSystemColor(SWT.COLOR_GREEN);
	Bullet bullet2 = new Bullet (ST.BULLET_TEXT, style2);
	bullet2.text = "\u2713";
	StyleRange style3 = new StyleRange();
	style3.metrics = new GlyphMetrics(0, 0, 50);
	Bullet bullet3 = new Bullet (ST.BULLET_CUSTOM, style2);

	styledText.setLineBullet(2, 4, bullet0);
	styledText.setLineBullet(7, 2, bullet1);
	styledText.setLineBullet(9, 4, bullet2);
	styledText.setLineBullet(13, 2, bullet1);
	styledText.setLineBullet(16, 4, bullet3);

	styledText.addPaintObjectListener(new PaintObjectListener() {
		public void paintObject(PaintObjectEvent event) {
			Display display = event.display;
			StyleRange style = event.style;
			Font font = style.font;
			if (font == null) font = styledText.getFont();
			TextLayout layout = new TextLayout(display);
			layout.setAscent(event.ascent);
			layout.setDescent(event.descent);
			layout.setFont(font);
			layout.setText("\u2023 1." + event.bulletIndex + ")");
			layout.draw(event.gc, event.x + 10, event.y);
			layout.dispose();
		}
	});
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10138, 1, '[SWT]SnippetSnippet223.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
/* 
 * example snippet: ExpandBar example
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

public class Snippet223 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	shell.setText("ExpandBar Example");
	ExpandBar bar = new ExpandBar (shell, SWT.V_SCROLL);
	Image image = display.getSystemImage(SWT.ICON_QUESTION);
	
	// First item
	Composite composite = new Composite (bar, SWT.NONE);
	GridLayout layout = new GridLayout ();
	layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
	layout.verticalSpacing = 10;
	composite.setLayout(layout);
	Button button = new Button (composite, SWT.PUSH);
	button.setText("SWT.PUSH");
	button = new Button (composite, SWT.RADIO);
	button.setText("SWT.RADIO");
	button = new Button (composite, SWT.CHECK);
	button.setText("SWT.CHECK");
	button = new Button (composite, SWT.TOGGLE);
	button.setText("SWT.TOGGLE");
	ExpandItem item0 = new ExpandItem (bar, SWT.NONE, 0);
	item0.setText("What is your favorite button");
	item0.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	item0.setControl(composite);
	item0.setImage(image);
	
	// Second item
	composite = new Composite (bar, SWT.NONE);
	layout = new GridLayout (2, false);
	layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
	layout.verticalSpacing = 10;
	composite.setLayout(layout);	
	Label label = new Label (composite, SWT.NONE);
	label.setImage(display.getSystemImage(SWT.ICON_ERROR));
	label = new Label (composite, SWT.NONE);
	label.setText("SWT.ICON_ERROR");
	label = new Label (composite, SWT.NONE);
	label.setImage(display.getSystemImage(SWT.ICON_INFORMATION));
	label = new Label (composite, SWT.NONE);
	label.setText("SWT.ICON_INFORMATION");
	label = new Label (composite, SWT.NONE);
	label.setImage(display.getSystemImage(SWT.ICON_WARNING));
	label = new Label (composite, SWT.NONE);
	label.setText("SWT.ICON_WARNING");
	label = new Label (composite, SWT.NONE);
	label.setImage(display.getSystemImage(SWT.ICON_QUESTION));
	label = new Label (composite, SWT.NONE);
	label.setText("SWT.ICON_QUESTION");
	ExpandItem item1 = new ExpandItem (bar, SWT.NONE, 1);
	item1.setText("What is your favorite icon");
	item1.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	item1.setControl(composite);
	item1.setImage(image);
	
	// Third item
	composite = new Composite (bar, SWT.NONE);
	layout = new GridLayout (2, true);
	layout.marginLeft = layout.marginTop = layout.marginRight = layout.marginBottom = 10;
	layout.verticalSpacing = 10;
	composite.setLayout(layout);
	label = new Label (composite, SWT.NONE);
	label.setText("Scale");	
	new Scale (composite, SWT.NONE);
	label = new Label (composite, SWT.NONE);
	label.setText("Spinner");	
	new Spinner (composite, SWT.BORDER);
	label = new Label (composite, SWT.NONE);
	label.setText("Slider");	
	new Slider (composite, SWT.NONE);
	ExpandItem item2 = new ExpandItem (bar, SWT.NONE, 2);
	item2.setText("What is your favorite range widget");
	item2.setHeight(composite.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
	item2.setControl(composite);
	item2.setImage(image);
	
	item1.setExpanded(true);
	bar.setSpacing(8);
	shell.setSize(400, 350);
	shell.open();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) {
			display.sleep ();
		}
	}
	image.dispose();
	display.dispose();
}

}
', now(), now());
insert into SNIPPET values (10139, 1, '[SWT]SnippetSnippet224.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * implement radio behavior for setSelection()
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet224 {
public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setLayout (new RowLayout (SWT.VERTICAL));
	for (int i=0; i<8; i++) {
		Button button = new Button (shell, SWT.RADIO);
		button.setText ("B" + i);
		if (i == 0) button.setSelection (true);
	}
	Button button = new Button (shell, SWT.PUSH);
	button.setText ("Set Selection to B4");
	button.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event event) {
			Control [] children = shell.getChildren ();
			Button newButton = (Button) children [4];
			for (int i=0; i<children.length; i++) {
				Control child = children [i];
				if (child instanceof Button && (child.getStyle () & SWT.RADIO) != 0) {
					((Button) child).setSelection (false);
				}
			}
			newButton.setSelection (true);
		}
	});
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10140, 1, '[SWT]SnippetSnippet225.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
  
/*
 * Tooltip example snippet: create a balloon tooltip for a tray item
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet225 {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	Image image = null;
	final ToolTip tip = new ToolTip(shell, SWT.BALLOON | SWT.ICON_INFORMATION);
	tip.setMessage("Here is a message for the user. When the message is too long it wraps. I should say something cool but nothing comes to my mind.");
	Tray tray = display.getSystemTray();
	if (tray != null) {
		TrayItem item = new TrayItem(tray, SWT.NONE);
		image = display.getSystemImage(SWT.ICON_INFORMATION);
		item.setImage(image);
		tip.setText("Notification from a tray item");
		item.setToolTip(tip);
	} else {
		tip.setText("Notification from anywhere");
		tip.setLocation(400, 400);
	}
	Button button = new Button (shell, SWT.PUSH);
	button.setText("Press for balloon tip");
	button.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event event) {
			tip.setVisible(true);
		}
	});
	button.pack();
	shell.setBounds(50, 50, 300, 200);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	if (image != null) image.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10141, 1, '[SWT]SnippetSnippet226.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Tree example snippet: Draw a custom gradient selection
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.*;

public class Snippet226 {
	
public static void main(String [] args) {
	final Display display = new Display();
	Shell shell = new Shell(display);
	shell.setText("Custom gradient selection for Tree");
	shell.setLayout(new FillLayout());
	final Tree tree = new Tree(shell, SWT.MULTI | SWT.FULL_SELECTION);
	tree.setHeaderVisible(true);
	tree.setLinesVisible(true);
	int columnCount = 4;
	for (int i=0; i<columnCount; i++) {
		TreeColumn column = new TreeColumn(tree, SWT.NONE);
		column.setText("Column " + i);	
	}
	int itemCount = 3;
	for (int i=0; i<itemCount; i++) {
		TreeItem item1 = new TreeItem(tree, SWT.NONE);
		item1.setText("item "+i);
		for (int c=1; c < columnCount; c++) {
			item1.setText(c, "item ["+i+"-"+c+"]");
		}
		for (int j=0; j<itemCount; j++) {
			TreeItem item2 = new TreeItem(item1, SWT.NONE);
			item2.setText("item ["+i+" "+j+"]");
			for (int c=1; c<columnCount; c++) {
				item2.setText(c, "item ["+i+" "+j+"-"+c+"]");
			}
			for (int k=0; k<itemCount; k++) {
				TreeItem item3 = new TreeItem(item2, SWT.NONE);
				item3.setText("item ["+i+" "+j+" "+k+"]");
				for (int c=1; c<columnCount; c++) {
					item3.setText(c, "item ["+i+" "+j+" "+k+"-"+c+"]");
				}
			}
		}
	}

	/*
	 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly.
	 * Therefore, it is critical for performance that these methods be
	 * as efficient as possible.
	 */
	tree.addListener(SWT.EraseItem, new Listener() {
		public void handleEvent(Event event) {
			event.detail &= ~SWT.HOT;
			if ((event.detail & SWT.SELECTED) != 0) {
				GC gc = event.gc;
				Rectangle area = tree.getClientArea();
				/*
				 * If you wish to paint the selection beyond the end of
				 * last column, you must change the clipping region.
				 */
				int columnCount = tree.getColumnCount();
				if (event.index == columnCount - 1 || columnCount == 0) {
					int width = area.x + area.width - event.x;
					if (width > 0) {
						Region region = new Region();
						gc.getClipping(region);
						region.add(event.x, event.y, width, event.height); 
						gc.setClipping(region);
						region.dispose();
					}
				}
				gc.setAdvanced(true);
				if (gc.getAdvanced()) gc.setAlpha(127);								
				Rectangle rect = event.getBounds();
				Color foreground = gc.getForeground();
				Color background = gc.getBackground();
				gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
				gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_BACKGROUND));
				gc.fillGradientRectangle(0, rect.y, 500, rect.height, false);
				// restore colors for subsequent drawing
				gc.setForeground(foreground);
				gc.setBackground(background);
				event.detail &= ~SWT.SELECTED;					
			}						
		}
	});		
	for (int i=0; i<columnCount; i++) {
		tree.getColumn(i).pack();
	}	
	tree.setSelection(tree.getItem(0));
	shell.setSize(500, 200);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();	
}
}', now(), now());
insert into SNIPPET values (10142, 1, '[SWT]SnippetSnippet227.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
/* 
 * Tree example snippet: Multiple lines in a TreeItem
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet227 {	
public static void main(String [] args) {
	Display display = new Display();
	Shell shell = new Shell (display);
	shell.setText("Multiple lines in a TreeItem");
	shell.setLayout (new FillLayout());
	final Tree tree = new Tree(shell, SWT.MULTI | SWT.FULL_SELECTION);
	tree.setHeaderVisible(true);
	tree.setLinesVisible(true);
	int columnCount = 4;
	for (int i=0; i<columnCount; i++) {
		TreeColumn column = new TreeColumn(tree, SWT.NONE);
		column.setText("Column " + i);
		column.setWidth(100);
	}
	int itemCount = 3;
	for (int i=0; i<itemCount; i++) {
		TreeItem item1 = new TreeItem(tree, SWT.NONE);
		item1.setText("item "+i);
		for (int c=1; c < columnCount; c++) {
			item1.setText(c, "item ["+i+"-"+c+"]");
		}
		for (int j=0; j<itemCount; j++) {
			TreeItem item2 = new TreeItem(item1, SWT.NONE);
			item2.setText("item ["+i+" "+j+"]");
			for (int c=1; c<columnCount; c++) {
				item2.setText(c, "item ["+i+" "+j+"-"+c+"]");
			}
			for (int k=0; k<itemCount; k++) {
				TreeItem item3 = new TreeItem(item2, SWT.NONE);
				item3.setText("item ["+i+" "+j+" "+k+"]");
				for (int c=1; c<columnCount; c++) {
					item3.setText(c, "item ["+i+" "+j+" "+k+"-"+c+"]");
				}
			}
		}
	}

	/*
	 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly.
	 * Therefore, it is critical for performance that these methods be
	 * as efficient as possible.
	 */
	Listener paintListener = new Listener() {
		public void handleEvent(Event event) {
			switch(event.type) {		
				case SWT.MeasureItem: {
					TreeItem item = (TreeItem)event.item;
					String text = getText(item, event.index);
					Point size = event.gc.textExtent(text);
					event.width = size.x;
					event.height = Math.max(event.height, size.y);
					break;
				}
				case SWT.PaintItem: {
					TreeItem item = (TreeItem)event.item;
					String text = getText(item, event.index);
					Point size = event.gc.textExtent(text);					
					int offset2 = event.index == 0 ? Math.max(0, (event.height - size.y) / 2) : 0;
					event.gc.drawText(text, event.x, event.y + offset2, true);
					break;
				}
				case SWT.EraseItem: {	
					event.detail &= ~SWT.FOREGROUND;
					break;
				}
			}
		}
		String getText(TreeItem item, int column) {
			String text = item.getText(column);
			if (column != 0) {
				TreeItem parent = item.getParentItem();
				int index = parent == null ? tree.indexOf(item) : parent.indexOf(item);
				if ((index+column) % 3 == 1){
					text +="\nnew line";
				}
				if ((index+column) % 3 == 2) {
					text +="\nnew line\nnew line";
				}
			}
			return text;
		}
	};
	tree.addListener(SWT.MeasureItem, paintListener);
	tree.addListener(SWT.PaintItem, paintListener);
	tree.addListener(SWT.EraseItem, paintListener);

	shell.setSize(600, 400);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}', now(), now());
insert into SNIPPET values (10143, 1, '[SWT]SnippetSnippet228.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Table example snippet: Draw a bar graph
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet228 {
	
public static void main(String [] args) {
	final Display display = new Display();		
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	shell.setText("Show results as a bar chart in Table");
	final Table table = new Table(shell, SWT.BORDER);
	table.setHeaderVisible(true);
	table.setLinesVisible(true);
	TableColumn column1 = new TableColumn(table, SWT.NONE);
	column1.setText("Bug Status");
	column1.setWidth(100);
	final TableColumn column2 = new TableColumn(table, SWT.NONE);
	column2.setText("Percent");
	column2.setWidth(200);
	String[] labels = new String[]{"Resolved", "New", "Wont Fix", "Invalid"};
	for (int i=0; i<labels.length; i++) {
		 TableItem item = new TableItem(table, SWT.NONE);
		 item.setText(labels[i]);
	}

	/*
	 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly.
	 * Therefore, it is critical for performance that these methods be
	 * as efficient as possible.
	 */
	table.addListener(SWT.PaintItem, new Listener() {
		int[] percents = new int[] {50, 30, 5, 15};
		public void handleEvent(Event event) {
			if (event.index == 1) {
				GC gc = event.gc;
				TableItem item = (TableItem)event.item;
				int index = table.indexOf(item);
				int percent = percents[index];
				Color foreground = gc.getForeground();
				Color background = gc.getBackground();
				gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
				gc.setBackground(display.getSystemColor(SWT.COLOR_YELLOW));
				int width = (column2.getWidth() - 1) * percent / 100;
				gc.fillGradientRectangle(event.x, event.y, width, event.height, true);					
				Rectangle rect2 = new Rectangle(event.x, event.y, width-1, event.height-1);
				gc.drawRectangle(rect2);
				gc.setForeground(display.getSystemColor(SWT.COLOR_LIST_FOREGROUND));
				String text = percent+"%";
				Point size = event.gc.textExtent(text);					
				int offset = Math.max(0, (event.height - size.y) / 2);
				gc.drawText(text, event.x+2, event.y+offset, true);
				gc.setForeground(background);
				gc.setBackground(foreground);
			}
		}
	});		
			
	shell.pack();
	shell.open();
	while(!shell.isDisposed()) {
		if(!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}', now(), now());
insert into SNIPPET values (10144, 1, '[SWT]SnippetSnippet229.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Table example snippet: Draw a custom gradient selection
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet229 {
public static void main(String [] args) {
	final Display display = new Display();
	Shell shell = new Shell(display);
	shell.setText("Custom gradient selection for Table");
	shell.setLayout(new FillLayout());
	final Table table = new Table(shell, SWT.MULTI | SWT.FULL_SELECTION);
	table.setHeaderVisible(true);
	table.setLinesVisible(true);
	int columnCount = 3;
	for (int i=0; i<columnCount; i++) {
		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText("Column " + i);	
	}
	int itemCount = 8;
	for(int i = 0; i < itemCount; i++) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[] {"item "+i+" a", "item "+i+" b", "item "+i+" c"});
	}		
	/*
	 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly.
	 * Therefore, it is critical for performance that these methods be
	 * as efficient as possible.
	 */
	table.addListener(SWT.EraseItem, new Listener() {
		public void handleEvent(Event event) {
			event.detail &= ~SWT.HOT;	
			if((event.detail & SWT.SELECTED) != 0) {
				GC gc = event.gc;
				Rectangle area = table.getClientArea();
				/*
				 * If you wish to paint the selection beyond the end of
				 * last column, you must change the clipping region.
				 */
				int columnCount = table.getColumnCount();
				if (event.index == columnCount - 1 || columnCount == 0) {
					int width = area.x + area.width - event.x;
					if (width > 0) {
						Region region = new Region();
						gc.getClipping(region);
						region.add(event.x, event.y, width, event.height); 
						gc.setClipping(region);
						region.dispose();
					}
				}
				gc.setAdvanced(true);
				if (gc.getAdvanced()) gc.setAlpha(127);								
				Rectangle rect = event.getBounds();
				Color foreground = gc.getForeground();
				Color background = gc.getBackground();
				gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
				gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_BACKGROUND));
				gc.fillGradientRectangle(0, rect.y, 500, rect.height, false);
				// restore colors for subsequent drawing
				gc.setForeground(foreground);
				gc.setBackground(background);
				event.detail &= ~SWT.SELECTED;					
			}						
		}
	});		
	for (int i=0; i<columnCount; i++) {
		table.getColumn(i).pack();
	}	
	table.setSelection(table.getItem(0));
	shell.setSize(500, 200);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();	
}
}', now(), now());
insert into SNIPPET values (10145, 1, '[SWT]SnippetSnippet23.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tracker example snippet: create a tracker (drag on mouse down)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet23 {

public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.open ();
	shell.addListener (SWT.MouseDown, new Listener () {
		public void handleEvent (Event e) {
			Tracker tracker = new Tracker (shell, SWT.NONE);
			tracker.setRectangles (new Rectangle [] {
				new Rectangle (e.x, e.y, 100, 100),
			});
			tracker.open ();
		}
	});
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10146, 1, '[SWT]SnippetSnippet230.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Table example snippet: Images on the right hand side of a TableItem
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet230 {
	
public static void main(String [] args) {
	Display display = new Display();
	final Image image = display.getSystemImage(SWT.ICON_INFORMATION);
	Shell shell = new Shell(display);
	shell.setText("Images on the right side of the TableItem");
	shell.setLayout(new FillLayout ());
	Table table = new Table(shell, SWT.MULTI | SWT.FULL_SELECTION);
	table.setHeaderVisible(true);
	table.setLinesVisible(true);			
	int columnCount = 3;
	for (int i=0; i<columnCount; i++) {
		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText("Column " + i);	
	}
	int itemCount = 8;
	for(int i = 0; i < itemCount; i++) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[] {"item "+i+" a", "item "+i+" b", "item "+i+" c"});
	}
	/*
	 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly.
	 * Therefore, it is critical for performance that these methods be
	 * as efficient as possible.
	 */
	Listener paintListener = new Listener() {
		public void handleEvent(Event event) {		
			switch(event.type) {
				case SWT.MeasureItem: {
					Rectangle rect = image.getBounds();
					event.width += rect.width;
					event.height = Math.max(event.height, rect.height + 2);
					break;
				}
				case SWT.PaintItem: {
					int x = event.x + event.width;
					Rectangle rect = image.getBounds();
					int offset = Math.max(0, (event.height - rect.height) / 2);
					event.gc.drawImage(image, x, event.y + offset);
					break;
				}
			}
		}
	};		
	table.addListener(SWT.MeasureItem, paintListener);
	table.addListener(SWT.PaintItem, paintListener);		

	for(int i = 0; i < columnCount; i++) {
		table.getColumn(i).pack();
	}	
	shell.setSize(500, 200);
	shell.open();
	while(!shell.isDisposed ()) {
		if(!display.readAndDispatch()) display.sleep();
	}
	if(image != null) image.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10147, 1, '[SWT]SnippetSnippet231.java', '/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * example snippet: Multiple lines per TableItem
 *
 * For a detailed explanation of this snippet see
 * http://www.eclipse.org/articles/Article-CustomDrawingTableAndTreeItems/customDraw.htm#_example6
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet231 {
	
public static void main(String [] args) {
	final int COLUMN_COUNT = 4;
	final int ITEM_COUNT = 8;
	final int TEXT_MARGIN = 3;
	Display display = new Display();
	Shell shell = new Shell(display);
	final Table table = new Table(shell, SWT.FULL_SELECTION);
	table.setHeaderVisible(true);
	table.setLinesVisible(true);
	for (int i = 0; i < COLUMN_COUNT; i++) {
		new TableColumn(table, SWT.NONE);
	}
	for (int i = 0; i < ITEM_COUNT; i++) {
		TableItem item = new TableItem(table, SWT.NONE);
		for (int j = 0; j < COLUMN_COUNT; j++) {
			String string = "item " + i + " col " + j;
			if ((i + j) % 3 == 1) {
				string +="\nnew line1";
			}
			if ((i + j) % 3 == 2) {
				string +="\nnew line1\nnew line2";
			}
			item.setText(j, string);
		}
	}

	/*
	 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly.
	 * Therefore, it is critical for performance that these methods be
	 * as efficient as possible.
	 */
	table.addListener(SWT.MeasureItem, new Listener() {
		public void handleEvent(Event event) {
			TableItem item = (TableItem)event.item;
			String text = item.getText(event.index);
			Point size = event.gc.textExtent(text);
			event.width = size.x + 2 * TEXT_MARGIN;
			event.height = Math.max(event.height, size.y + TEXT_MARGIN);
		}
	});
	table.addListener(SWT.EraseItem, new Listener() {
		public void handleEvent(Event event) {
			event.detail &= ~SWT.FOREGROUND;
		}
	});
	table.addListener(SWT.PaintItem, new Listener() {
		public void handleEvent(Event event) {
			TableItem item = (TableItem)event.item;
			String text = item.getText(event.index);
			/* center column 1 vertically */
			int yOffset = 0;
			if (event.index == 1) {
				Point size = event.gc.textExtent(text);
				yOffset = Math.max(0, (event.height - size.y) / 2);
			}
			event.gc.drawText(text, event.x + TEXT_MARGIN, event.y + yOffset, true);
		}
	});

	for (int i = 0; i < COLUMN_COUNT; i++) {
		table.getColumn(i).pack();
	}
	table.pack();
	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10148, 1, '[SWT]SnippetSnippet232.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Tree example snippet: Draw a bar graph
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet232 {
	
public static void main(String [] args) {
	final Display display = new Display();		
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	shell.setText("Show results as a bar chart in Tree");
	final Tree tree = new Tree(shell, SWT.BORDER);
	tree.setHeaderVisible(true);
	tree.setLinesVisible(true);
	TreeColumn column1 = new TreeColumn(tree, SWT.NONE);
	column1.setText("Bug Status");
	column1.setWidth(100);
	final TreeColumn column2 = new TreeColumn(tree, SWT.NONE);
	column2.setText("Percent");
	column2.setWidth(200);
	String[] states = new String[]{"Resolved", "New", "Wont Fix", "Invalid"};
	String[] teams = new String[] {"UI", "SWT", "OSGI"};
	for (int i=0; i<teams.length; i++) {
		TreeItem item = new TreeItem(tree, SWT.NONE);
		item.setText(teams[i]);
		for (int j = 0; j < states.length; j++) {
			TreeItem subItem = new TreeItem(item, SWT.NONE);
			subItem.setText(states[j]);	
		}
	}

	/*
	 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly.
	 * Therefore, it is critical for performance that these methods be
	 * as efficient as possible.
	 */
	tree.addListener(SWT.PaintItem, new Listener() {
		int[] percents = new int[] {50, 30, 5, 15};
		public void handleEvent(Event event) {
			if (event.index == 1) {
				TreeItem item = (TreeItem)event.item;
				TreeItem parent = item.getParentItem();
				if (parent != null) {
					GC gc = event.gc;
					int index = parent.indexOf(item);
					int percent = percents[index];
					Color foreground = gc.getForeground();
					Color background = gc.getBackground();
					gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
					gc.setBackground(display.getSystemColor(SWT.COLOR_YELLOW));
					int width = (column2.getWidth() - 1) * percent / 100;
					gc.fillGradientRectangle(event.x, event.y, width, event.height, true);					
					Rectangle rect2 = new Rectangle(event.x, event.y, width-1, event.height-1);
					gc.drawRectangle(rect2);
					gc.setForeground(display.getSystemColor(SWT.COLOR_LIST_FOREGROUND));
					String text = percent+"%";
					Point size = event.gc.textExtent(text);					
					int offset = Math.max(0, (event.height - size.y) / 2);
					gc.drawText(text, event.x+2, event.y+offset, true);
					gc.setForeground(background);
					gc.setBackground(foreground);
				}
			}
		}
	});		
			
	shell.pack();
	shell.open();
	while(!shell.isDisposed()) {
		if(!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}', now(), now());
insert into SNIPPET values (10149, 1, '[SWT]SnippetSnippet233.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Shell example snippet: create a dialog shell and position it
 * with upper left corner at cursor position
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;

public class Snippet233 {
	public static void main (String [] args) {
		final Display display = new Display ();
		final Shell shell = new Shell (display);
		shell.setText ("Parent Shell");
		shell.addMouseListener (new MouseAdapter() {
			public void mouseDown (MouseEvent e) {
				Shell dialog = new Shell (shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
				Point pt = display.getCursorLocation ();
				dialog.setLocation (pt.x, pt.y);
				dialog.setText ("Dialog Shell");
				dialog.setSize (100, 100);
				dialog.open (); 
			}});
		shell.setSize (400, 400);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}
}
', now(), now());
insert into SNIPPET values (10150, 1, '[SWT]SnippetSnippet234.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
/* 
 * Table example snippet: Fixed first column and horizontal scroll remaining columns
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet234 {
public static void main (String [] args) {
	int rowCount = 40;
	int columnCount = 15;
	final Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	
	Composite parent = new Composite(shell, SWT.BORDER);
	GridLayout layout = new GridLayout(2, false);
	layout.marginWidth = layout.marginHeight = layout.horizontalSpacing = 0;
	parent.setLayout(layout);
	final Table leftTable = new Table(parent, SWT.MULTI | SWT.FULL_SELECTION);
	leftTable.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));
	leftTable.setHeaderVisible(true);
	final Table rightTable = new Table(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION);
	rightTable.setHeaderVisible(true);
	GridData table2Data = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 2);
	rightTable.setLayoutData(table2Data);
	// Create columns
	TableColumn column1 = new TableColumn(leftTable, SWT.NONE);
	column1.setText("Name");
	column1.setWidth(150);
	for (int i = 0; i < columnCount; i++) {
		TableColumn column = new TableColumn(rightTable, SWT.NONE);
		column.setText("Value "+i);
		column.setWidth(200);
	}
	// Create rows
	for (int i = 0; i < rowCount; i++) {
		TableItem item = new TableItem(leftTable, SWT.NONE);
		item.setText("item "+i);
		item = new TableItem(rightTable, SWT.NONE);
		for (int j = 0; j < columnCount; j++) {
			item.setText(j, "Item "+i+" value @ "+j);
		}
	}
	// Make selection the same in both tables
	leftTable.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event event) {
			rightTable.setSelection(leftTable.getSelectionIndices());
		}
	});
	rightTable.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event event) {
			leftTable.setSelection(rightTable.getSelectionIndices());
		}
	});
	// On Windows, the selection is gray if the table does not have focus.
	// To make both tables appear in focus, draw the selection background here.
	// This part only works on version 3.2 or later.
	Listener eraseListener = new Listener() {
		public void handleEvent(Event event) {
			event.detail &= ~SWT.HOT;
			if((event.detail & SWT.SELECTED) != 0) {
				GC gc = event.gc;
				Rectangle rect = event.getBounds();
				gc.setForeground(display.getSystemColor(SWT.COLOR_LIST_SELECTION_TEXT));
				gc.setBackground(display.getSystemColor(SWT.COLOR_LIST_SELECTION));
				gc.fillRectangle(rect);
				event.detail &= ~SWT.SELECTED;					
			}
		}
	};
	
	leftTable.addListener(SWT.EraseItem, eraseListener);
	rightTable.addListener(SWT.EraseItem, eraseListener);
	// Make vertical scrollbars scroll together
	ScrollBar vBarLeft = leftTable.getVerticalBar();
	vBarLeft.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event event) {
			rightTable.setTopIndex(leftTable.getTopIndex());
		}
	});
	ScrollBar vBarRight = rightTable.getVerticalBar();
	vBarRight.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event event) {
			leftTable.setTopIndex(rightTable.getTopIndex());
		}
	});
	// Horizontal bar on second table takes up a little extra space.
	// To keep vertical scroll bars in sink, force table1 to end above
	// horizontal scrollbar
	ScrollBar hBarRight = rightTable.getHorizontalBar();
	Label spacer = new Label(parent, SWT.NONE);
	GridData spacerData = new GridData();
	spacerData.heightHint = hBarRight.getSize().y;
	spacer.setVisible(false);
	parent.setBackground(leftTable.getBackground());
	
	shell.setSize(600, 400);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10151, 1, '[SWT]SnippetSnippet235.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
/* 
 * example snippet: detect a system settings change
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet235 {


public static void main(String [] args) {
	final Display display = new Display();
	final Shell shell = new Shell(display);
	shell.setText("The SWT.Settings Event");
	shell.setLayout(new GridLayout());
	Label label = new Label(shell, SWT.WRAP);
	label.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
	label.setText("Change a system setting and the table below will be updated.");
	final Table table = new Table(shell, SWT.BORDER);
	table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	TableColumn column = new TableColumn(table, SWT.NONE);
	column = new TableColumn(table, SWT.NONE);
	column.setWidth(150);
	column = new TableColumn(table, SWT.NONE);
	for (int i = 0; i < colorIds.length; i++) {
		TableItem item = new TableItem(table, SWT.NONE);
		Color color = display.getSystemColor(colorIds[i]);
		item.setText(0, colorNames[i]);
		item.setBackground(1, color);
		item.setText(2, color.toString());
	}
	TableColumn[] columns = table.getColumns();
	columns[0].pack();
	columns[2].pack();
	display.addListener(SWT.Settings, new Listener() {
		public void handleEvent(Event event) {
			for (int i = 0; i < colorIds.length; i++) {
				Color color = display.getSystemColor(colorIds[i]);
				TableItem item = table.getItem(i);
				item.setBackground(1, color);
			}
			TableColumn[] columns = table.getColumns();
			columns[0].pack();
			columns[2].pack();
		}
	});

	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
static int[] colorIds = new int[] {SWT.COLOR_INFO_BACKGROUND, 
		SWT.COLOR_INFO_FOREGROUND, 
		SWT.COLOR_LIST_BACKGROUND,
		SWT.COLOR_LIST_FOREGROUND,
		SWT.COLOR_LIST_SELECTION,
		SWT.COLOR_LIST_SELECTION_TEXT,
		SWT.COLOR_TITLE_BACKGROUND,
		SWT.COLOR_TITLE_BACKGROUND_GRADIENT,
		SWT.COLOR_TITLE_FOREGROUND,
		SWT.COLOR_TITLE_INACTIVE_BACKGROUND,
		SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT,
		SWT.COLOR_TITLE_INACTIVE_FOREGROUND,
		SWT.COLOR_WIDGET_BACKGROUND,
		SWT.COLOR_WIDGET_BORDER,
		SWT.COLOR_WIDGET_DARK_SHADOW,
		SWT.COLOR_WIDGET_FOREGROUND,
		SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW,
		SWT.COLOR_WIDGET_LIGHT_SHADOW,
		SWT.COLOR_WIDGET_NORMAL_SHADOW,};
static String [] colorNames = new String[] {"SWT.COLOR_INFO_BACKGROUND",
		"SWT.COLOR_INFO_FOREGROUND", 
		"SWT.COLOR_LIST_BACKGROUND",
		"SWT.COLOR_LIST_FOREGROUND",
		"SWT.COLOR_LIST_SELECTION",
		"SWT.COLOR_LIST_SELECTION_TEXT",
		"SWT.COLOR_TITLE_BACKGROUND",
		"SWT.COLOR_TITLE_BACKGROUND_GRADIENT",
		"SWT.COLOR_TITLE_FOREGROUND",
		"SWT.COLOR_TITLE_INACTIVE_BACKGROUND",
		"SWT.COLOR_TITLE_INACTIVE_BACKGROUND_GRADIENT",
		"SWT.COLOR_TITLE_INACTIVE_FOREGROUND",
		"SWT.COLOR_WIDGET_BACKGROUND",
		"SWT.COLOR_WIDGET_BORDER",
		"SWT.COLOR_WIDGET_DARK_SHADOW",
		"SWT.COLOR_WIDGET_FOREGROUND",
		"SWT.COLOR_WIDGET_HIGHLIGHT_SHADOW",
		"SWT.COLOR_WIDGET_LIGHT_SHADOW",
		"SWT.COLOR_WIDGET_NORMAL_SHADOW",};
}
', now(), now());
insert into SNIPPET values (10152, 1, '[SWT]SnippetSnippet236.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
/* 
 * Table example snippet: draw different foreground colors for text in a TableItem.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet236 {

public static void main(String [] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setText("Table: Change style multiple times in cell");
	shell.setLayout(new FillLayout());
	Table table = new Table(shell, SWT.MULTI | SWT.FULL_SELECTION);
	table.setLinesVisible(true);
	for(int i = 0; i < 10; i++) {
		new TableItem(table, SWT.NONE);			
	}
	final TextLayout textLayout = new TextLayout(display);
	textLayout.setText("SWT: Standard Widget Toolkit");
	Font font1 = new Font(display, "Tahoma", 14, SWT.BOLD);
	Font font2 = new Font(display, "Tahoma", 10, SWT.NORMAL);
	Font font3 = new Font(display, "Tahoma", 14, SWT.ITALIC);
	TextStyle style1 = new TextStyle(font1, display.getSystemColor(SWT.COLOR_BLUE), null);
	TextStyle style2 = new TextStyle(font2, display.getSystemColor(SWT.COLOR_MAGENTA), null);
	TextStyle style3 = new TextStyle(font3, display.getSystemColor(SWT.COLOR_RED), null);
	textLayout.setStyle(style1, 0, 0); textLayout.setStyle(style1, 5, 12);
	textLayout.setStyle(style2, 1, 1); textLayout.setStyle(style2, 14, 19);
	textLayout.setStyle(style3, 2, 2); textLayout.setStyle(style3, 21, 27);

	/*
	 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly.
	 * Therefore, it is critical for performance that these methods be
	 * as efficient as possible.
	 */
	table.addListener(SWT.PaintItem, new Listener() {
		public void handleEvent(Event event) {
			textLayout.draw(event.gc, event.x, event.y);
		}
	});
	final Rectangle textLayoutBounds = textLayout.getBounds();
	table.addListener(SWT.MeasureItem, new Listener() {
		public void handleEvent(Event e) {
			e.width = textLayoutBounds.width + 2;
			e.height = textLayoutBounds.height + 2;
		}
	});
	shell.setSize(400, 200);
	shell.open();
	while(!shell.isDisposed()) {
		if(!display.readAndDispatch()) display.sleep();
	}
	font1.dispose();
	font2.dispose();
	font3.dispose();
	textLayout.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10153, 1, '[SWT]SnippetSnippet237.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
/* 
 * Composite Snippet: inherit a background color or image
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet237 {

public static void main(String[] args) {
	final Display display = new Display();
	final Shell shell = new Shell(display);
	shell.setText("Composite.setBackgroundMode()");
	shell.setLayout(new RowLayout(SWT.VERTICAL));
	
	Color color = display.getSystemColor(SWT.COLOR_CYAN);
	
	Group group = new Group(shell, SWT.NONE);
	group.setText("SWT.INHERIT_NONE");
	group.setBackground(color);
	group.setBackgroundMode(SWT.INHERIT_NONE);
	createChildren(group);
	
	group = new Group(shell, SWT.NONE);
	group.setBackground(color);
	group.setText("SWT.INHERIT_DEFAULT");
	group.setBackgroundMode(SWT.INHERIT_DEFAULT);
	createChildren(group);
	
	group = new Group(shell, SWT.NONE);
	group.setBackground(color);
	group.setText("SWT.INHERIT_FORCE");
	group.setBackgroundMode(SWT.INHERIT_FORCE);
	createChildren(group);
	
	shell.pack();
	shell.open();
	while(!shell.isDisposed()) {
		if(!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
static void createChildren(Composite parent) {
	parent.setLayout(new RowLayout());
	List list = new List(parent, SWT.BORDER | SWT.MULTI);
	list.add("List item 1");
	list.add("List item 2");
	Label label = new Label(parent, SWT.NONE);
	label.setText("Label");
	Button button = new Button(parent, SWT.RADIO);
	button.setText("Radio Button");
	button = new Button(parent, SWT.CHECK);
	button.setText("Check box Button");
	button = new Button(parent, SWT.PUSH);
	button.setText("Push Button");
	Text text = new Text(parent, SWT.BORDER);
	text.setText("Text");
}
}
', now(), now());
insert into SNIPPET values (10154, 1, '[SWT]SnippetSnippet238.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Menu example snippet: create a popup menu with a submenu
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet238 {

public static void main(String[] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Composite composite = new Composite (shell, SWT.BORDER);
	composite.setSize (100, 100);
	Menu menu = new Menu (shell, SWT.POP_UP);
	MenuItem item1 = new MenuItem (menu, SWT.PUSH);
	item1.setText ("Push Item");
	MenuItem item2 = new MenuItem (menu, SWT.CASCADE);
	item2.setText ("Cascade Item");
	Menu subMenu = new Menu (menu);
	item2.setMenu (subMenu);
	MenuItem subItem1 = new MenuItem (subMenu, SWT.PUSH);
	subItem1.setText ("Subitem 1");
	MenuItem subItem2 = new MenuItem (subMenu, SWT.PUSH);
	subItem2.setText ("Subitem 2");
	composite.setMenu (menu);
	shell.setMenu (menu);
	shell.setSize (300, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10155, 1, '[SWT]SnippetSnippet239.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Table snippet: make text span multiple columns
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet239 {
	
public static void main(String [] args) {
	final Display display = new Display();
	Shell shell = new Shell (display);
	shell.setText("Text spans two columns in a TableItem");
	shell.setLayout (new FillLayout());
	final Table table = new Table(shell, SWT.MULTI | SWT.FULL_SELECTION);
	table.setHeaderVisible(true);
	int columnCount = 4;
	for (int i=0; i<columnCount; i++) {
		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText("Column " + i);	
	}
	int itemCount = 8;
	for (int i = 0; i < itemCount; i++) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(0, "item "+i+" a");
		item.setText(3, "item "+i+" d");
	}	
	/*
	 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly.
	 * Therefore, it is critical for performance that these methods be
	 * as efficient as possible.
	 */
	final String string = "text that spans two columns";
	GC gc = new GC(table);
	final Point extent = gc.stringExtent(string);
	gc.dispose();
	final Color red = display.getSystemColor(SWT.COLOR_RED);
	Listener paintListener = new Listener() {
		public void handleEvent(Event event) {
			switch(event.type) {		
				case SWT.MeasureItem: {
					if (event.index == 1 || event.index == 2) {
						event.width = extent.x/2;
						event.height = Math.max(event.height, extent.y + 2);
					}
					break;
				}
				case SWT.PaintItem: {
					if (event.index == 1 || event.index == 2) {
						int offset = 0;
						if (event.index == 2) {
							TableColumn column1 = table.getColumn(1);
							offset = column1.getWidth();
						}
						event.gc.setForeground(red);
						int y = event.y + (event.height - extent.y)/2;
						event.gc.drawString(string, event.x - offset, y, true);
					}
					break;
				}
			}
		}
	};
	table.addListener(SWT.MeasureItem, paintListener);
	table.addListener(SWT.PaintItem, paintListener);
	for (int i = 0; i < columnCount; i++) {
		table.getColumn(i).pack();
	}
	shell.pack();
	shell.open();
	while(!shell.isDisposed()) {
		if(!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}', now(), now());
insert into SNIPPET values (10156, 1, '[SWT]SnippetSnippet24.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * example snippet: detect CR in a text or combo control (default selection)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet24 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new RowLayout ());
	Combo combo = new Combo (shell, SWT.NONE);
	combo.setItems (new String [] {"A-1", "B-1", "C-1"});
	Text text = new Text (shell, SWT.SINGLE | SWT.BORDER);
	text.setText ("some text");
	combo.addListener (SWT.DefaultSelection, new Listener () {
		public void handleEvent (Event e) {
			System.out.println (e.widget + " - Default Selection");
		}
	});
	text.addListener (SWT.DefaultSelection, new Listener () {
		public void handleEvent (Event e) {
			System.out.println (e.widget + " - Default Selection");
		}
	});
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10157, 1, '[SWT]SnippetSnippet240.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Tree snippet: Text that spans multiple columns
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet240 {

public static void main(String [] args) {
	final Display display = new Display();
	Shell shell = new Shell (display);
	shell.setText("Text spans two columns in a TreeItem");
	shell.setLayout (new FillLayout());
	final Tree tree = new Tree(shell, SWT.MULTI | SWT.FULL_SELECTION);
	tree.setHeaderVisible(true);
	int columnCount = 4;
	for (int i=0; i<columnCount; i++) {
		TreeColumn column = new TreeColumn(tree, SWT.NONE);
		column.setText("Column " + i);	
	}
	int itemCount = 8;
	for (int i = 0; i < itemCount; i++) {
		TreeItem item = new TreeItem(tree, SWT.NONE);
		item.setText(0, "item "+i+" a");
		item.setText(3, "item "+i+" d");
		for (int j = 0; j < 3; j++) {
			TreeItem subItem = new TreeItem(item, SWT.NONE);
			subItem.setText(0, "subItem "+i+"-"+j+" a");
			subItem.setText(3, "subItem "+i+"-"+j+" d");
		}
	}	
	/*
	 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly.
	 * Therefore, it is critical for performance that these methods be
	 * as efficient as possible.
	 */
	final String string = "text that spans two columns";
	GC gc = new GC(tree);
	final Point extent = gc.stringExtent(string);
	gc.dispose();
	final Color red = display.getSystemColor(SWT.COLOR_RED);
	Listener paintListener = new Listener() {
		public void handleEvent(Event event) {
			switch(event.type) {		
				case SWT.MeasureItem: {
					if (event.index == 1 || event.index == 2) {
						event.width = extent.x/2;
						event.height = Math.max(event.height, extent.y + 2);
					}
					break;
				}
				case SWT.PaintItem: {
					if (event.index == 1 || event.index == 2) {
						int offset = 0;
						if (event.index == 2) {
							TreeColumn column1 = tree.getColumn(1);
							offset = column1.getWidth();
						}
						event.gc.setForeground(red);
						int y = event.y + (event.height - extent.y)/2;
						event.gc.drawString(string, event.x - offset, y, true);
					}
					break;
				}
			}
		}
	};
	tree.addListener(SWT.MeasureItem, paintListener);
	tree.addListener(SWT.PaintItem, paintListener);
	for (int i = 0; i < columnCount; i++) {
		tree.getColumn(i).pack();
	}
	shell.pack();
	shell.open();
	while(!shell.isDisposed()) {
		if(!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}', now(), now());
insert into SNIPPET values (10158, 1, '[SWT]SnippetSnippet241.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Text snippet: Override Tab behavior to traverse out of a Text.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

public class Snippet241 {

public static void main(String [] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setBounds(10,10,200,200);
	Text text1 = new Text(shell, SWT.MULTI | SWT.WRAP);
	text1.setBounds(10,10,150,50);
	text1.setText("Tab will traverse out from here.");
	text1.addTraverseListener(new TraverseListener() {
		public void keyTraversed(TraverseEvent e) {
			if (e.detail == SWT.TRAVERSE_TAB_NEXT || e.detail == SWT.TRAVERSE_TAB_PREVIOUS) {
				e.doit = true;
			}
		}
	});
	Text text2 = new Text(shell, SWT.MULTI | SWT.WRAP);
	text2.setBounds(10,100,150,50);
	text2.setText("But Tab will NOT traverse out from here (Ctrl+Tab will).");
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10159, 1, '[SWT]SnippetSnippet242.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Cursor snippet: Hide the Cursor over a control.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet242 {

public static void main(String [] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setBounds(10, 10, 200, 200);
	Canvas canvas = new Canvas(shell, SWT.BORDER);
	canvas.setBounds(10,50,150,100);
	canvas.addPaintListener(new PaintListener() {
		public void paintControl(PaintEvent e) {
			e.gc.drawString("hide Cursor here", 10, 10);
		}
	});

	// create a cursor with a transparent image
	Color white = display.getSystemColor (SWT.COLOR_WHITE);
	Color black = display.getSystemColor (SWT.COLOR_BLACK);
	PaletteData palette = new PaletteData (new RGB [] {white.getRGB(), black.getRGB()});
	ImageData sourceData = new ImageData (16, 16, 1, palette);
	sourceData.transparentPixel = 0;
	Cursor cursor = new Cursor(display, sourceData, 0, 0);

	shell.open();
	canvas.setCursor(cursor);
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	cursor.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10160, 1, '[SWT]SnippetSnippet243.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Text snippet: type in one text, output to another
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;

public class Snippet243 {

public static void main(String [] args) {
	final Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout ());
	final Text text0 = new Text (shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	final Text text1 = new Text (shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	text0.addVerifyListener (new VerifyListener () {
		public void verifyText (VerifyEvent event) {
			text1.setTopIndex (text0.getTopIndex ());
			text1.setSelection (event.start, event.end);
			text1.insert (event.text);
		}
	});
	shell.setBounds(10, 10, 200, 200);
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10161, 1, '[SWT]SnippetSnippet244.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * StyledText snippet: Draw a box around text.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet244 {
	static String SEARCH_STRING = "box";
    public static void main(String[] args) {
        final Display display = new Display();
        final Color RED = display.getSystemColor(SWT.COLOR_RED);
        Shell shell = new Shell(display);
        shell.setBounds(10,10,250,250);
        final StyledText text = new StyledText(shell, SWT.NONE);
        text.setBounds(10,10,200,200);
        text.addListener(SWT.Paint, new Listener() {
			public void handleEvent(Event event) {
				String contents = text.getText();
				int stringWidth = event.gc.stringExtent(SEARCH_STRING).x;
				int lineHeight = text.getLineHeight();
				event.gc.setForeground(RED);
				int index = contents.indexOf(SEARCH_STRING);
				while (index != -1) {
					Point topLeft = text.getLocationAtOffset(index);
					event.gc.drawRectangle(topLeft.x - 1, topLeft.y, stringWidth + 1, lineHeight - 1);
					index = contents.indexOf(SEARCH_STRING, index + 1);
				}
			}
		});
        text.setText("This demonstrates drawing a box\naround every occurrence of the word\nbox in the StyledText");
        shell.open();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) display.sleep();
        }
        display.dispose();
    }
}
', now(), now());
insert into SNIPPET values (10162, 1, '[SWT]SnippetSnippet245.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Canvas snippet: paint a circle in a canvas
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;

public class Snippet245 {

public static void main(String [] args) {
	final Display display = new Display();
	final Shell shell = new Shell(display);
	shell.addPaintListener(new PaintListener() {
		public void paintControl(PaintEvent event) {
			Rectangle rect = shell.getClientArea();
			event.gc.drawOval(0, 0, rect.width - 1, rect.height - 1);
		}
	});
	shell.setBounds(10, 10, 200, 200);
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10163, 1, '[SWT]SnippetSnippet246.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Write an Image to a PNG file.
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet246 {

	public static void main(String[] args) {
		Display display = new Display();
		Font font = new Font(display, "Comic Sans MS", 24, SWT.BOLD);
		Image image = new Image(display, 87, 48);
		GC gc = new GC(image);
		gc.setBackground(display.getSystemColor(SWT.COLOR_WHITE));
		gc.fillRectangle(image.getBounds());
		gc.setFont(font);
		gc.setForeground(display.getSystemColor(SWT.COLOR_RED));
		gc.drawString("S", 3, 0);
		gc.setForeground(display.getSystemColor(SWT.COLOR_GREEN));
		gc.drawString("W", 25, 0);
		gc.setForeground(display.getSystemColor(SWT.COLOR_BLUE));
		gc.drawString("T", 62, 0);
		gc.dispose();

		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] {image.getImageData()};
		loader.save("swt.png", SWT.IMAGE_PNG);

		image.dispose();
		font.dispose();
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10164, 1, '[SWT]SnippetSnippet247.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Control example snippet: allow a multi-line text to process the default button
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet247 {
public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new RowLayout());
	Text text = new Text(shell, SWT.MULTI | SWT.BORDER);
	String modifier = SWT.MOD1 == SWT.CTRL ? "Ctrl" : "Command";
	text.setText("Hit " + modifier + "+Return\nto see\nthe default button\nrun");
	text.addTraverseListener(new TraverseListener () {
		public void keyTraversed(TraverseEvent e) {
			switch (e.detail) {
				case SWT.TRAVERSE_RETURN:
					if ((e.stateMask & SWT.MOD1) != 0) e.doit = true;
			}
		}
	});
	Button button = new Button (shell, SWT.PUSH);
	button.pack();
	button.setText("OK");
	button.addSelectionListener(new SelectionAdapter () {
		public void widgetSelected(SelectionEvent e) {
			System.out.println("OK selected");
		}
	});
	shell.setDefaultButton(button);
	shell.pack ();
	shell.open();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10165, 1, '[SWT]SnippetSnippet248.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Shell example snippet: allow escape to close a shell
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet248 {
public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	shell.addListener (SWT.Traverse, new Listener () {
		public void handleEvent (Event event) {
			switch (event.detail) {
				case SWT.TRAVERSE_ESCAPE:
					shell.close ();
					event.detail = SWT.TRAVERSE_NONE;
					event.doit = false;
					break;
			}
		}
	});
	Button button = new Button (shell, SWT.PUSH);
	button.setText ("A Button (that doesnt process Escape)");
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10166, 1, '[SWT]SnippetSnippet249.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * StackLayout example snippet: use a StackLayout to switch between Composites.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet249 {

	static int pageNum = -1;

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setBounds (10, 10, 300, 200);
	// create the composite that the pages will share
	final Composite contentPanel = new Composite (shell, SWT.BORDER);
	contentPanel.setBounds (100, 10, 190, 90);
	final StackLayout layout = new StackLayout ();
	contentPanel.setLayout (layout);

	// create the first pages content
	final Composite page0 = new Composite (contentPanel, SWT.NONE);
	page0.setLayout (new RowLayout ());
	Label label = new Label (page0, SWT.NONE);
	label.setText ("Label on page 1");
	label.pack ();

	// create the second pages content	
	final Composite page1 = new Composite (contentPanel, SWT.NONE);
	page1.setLayout (new RowLayout ());
	Button button = new Button (page1, SWT.NONE);
	button.setText ("Button on page 2");
	button.pack ();

	// create the button that will switch between the pages
	Button pageButton = new Button (shell, SWT.PUSH);
	pageButton.setText ("Push");
	pageButton.setBounds (10, 10, 80, 25);
	pageButton.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event event) {
			pageNum = ++pageNum % 2;
			layout.topControl = pageNum == 0 ? page0 : page1;
			contentPanel.layout ();
		}
	});

	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10167, 1, '[SWT]SnippetSnippet25.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Control example snippet: print key state, code and character
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet25 {

static String stateMask (int stateMask) {
	String string = "";
	if ((stateMask & SWT.CTRL) != 0) string += " CTRL";
	if ((stateMask & SWT.ALT) != 0) string += " ALT";
	if ((stateMask & SWT.SHIFT) != 0) string += " SHIFT";
	if ((stateMask & SWT.COMMAND) != 0) string += " COMMAND";
	return string;
}

static String character (char character) {
	switch (character) {
		case 0: 		return "\\0";
		case SWT.BS:	return "\\b";
		case SWT.CR:	return "\\r";
		case SWT.DEL:	return "DEL";
		case SWT.ESC:	return "ESC";
		case SWT.LF:	return "\\n";
		case SWT.TAB:	return "\\t";
	}
	return "" + character +"";
}

static String keyCode (int keyCode) {
	switch (keyCode) {
		
		/* Keyboard and Mouse Masks */
		case SWT.ALT: 		return "ALT";
		case SWT.SHIFT: 	return "SHIFT";
		case SWT.CONTROL:	return "CONTROL";
		case SWT.COMMAND:	return "COMMAND";
			
		/* Non-Numeric Keypad Keys */
		case SWT.ARROW_UP:		return "ARROW_UP";
		case SWT.ARROW_DOWN:	return "ARROW_DOWN";
		case SWT.ARROW_LEFT:	return "ARROW_LEFT";
		case SWT.ARROW_RIGHT:	return "ARROW_RIGHT";
		case SWT.PAGE_UP:		return "PAGE_UP";
		case SWT.PAGE_DOWN:		return "PAGE_DOWN";
		case SWT.HOME:			return "HOME";
		case SWT.END:			return "END";
		case SWT.INSERT:		return "INSERT";

		/* Virtual and Ascii Keys */
		case SWT.BS:	return "BS";
		case SWT.CR:	return "CR";		
		case SWT.DEL:	return "DEL";
		case SWT.ESC:	return "ESC";
		case SWT.LF:	return "LF";
		case SWT.TAB:	return "TAB";
	
		/* Functions Keys */
		case SWT.F1:	return "F1";
		case SWT.F2:	return "F2";
		case SWT.F3:	return "F3";
		case SWT.F4:	return "F4";
		case SWT.F5:	return "F5";
		case SWT.F6:	return "F6";
		case SWT.F7:	return "F7";
		case SWT.F8:	return "F8";
		case SWT.F9:	return "F9";
		case SWT.F10:	return "F10";
		case SWT.F11:	return "F11";
		case SWT.F12:	return "F12";
		case SWT.F13:	return "F13";
		case SWT.F14:	return "F14";
		case SWT.F15:	return "F15";
		
		/* Numeric Keypad Keys */
		case SWT.KEYPAD_ADD:		return "KEYPAD_ADD";
		case SWT.KEYPAD_SUBTRACT:	return "KEYPAD_SUBTRACT";
		case SWT.KEYPAD_MULTIPLY:	return "KEYPAD_MULTIPLY";
		case SWT.KEYPAD_DIVIDE:		return "KEYPAD_DIVIDE";
		case SWT.KEYPAD_DECIMAL:	return "KEYPAD_DECIMAL";
		case SWT.KEYPAD_CR:			return "KEYPAD_CR";
		case SWT.KEYPAD_0:			return "KEYPAD_0";
		case SWT.KEYPAD_1:			return "KEYPAD_1";
		case SWT.KEYPAD_2:			return "KEYPAD_2";
		case SWT.KEYPAD_3:			return "KEYPAD_3";
		case SWT.KEYPAD_4:			return "KEYPAD_4";
		case SWT.KEYPAD_5:			return "KEYPAD_5";
		case SWT.KEYPAD_6:			return "KEYPAD_6";
		case SWT.KEYPAD_7:			return "KEYPAD_7";
		case SWT.KEYPAD_8:			return "KEYPAD_8";
		case SWT.KEYPAD_9:			return "KEYPAD_9";
		case SWT.KEYPAD_EQUAL:		return "KEYPAD_EQUAL";

		/* Other keys */
		case SWT.CAPS_LOCK:		return "CAPS_LOCK";
		case SWT.NUM_LOCK:		return "NUM_LOCK";
		case SWT.SCROLL_LOCK:	return "SCROLL_LOCK";
		case SWT.PAUSE:			return "PAUSE";
		case SWT.BREAK:			return "BREAK";
		case SWT.PRINT_SCREEN:	return "PRINT_SCREEN";
		case SWT.HELP:			return "HELP";
	}
	return character ((char) keyCode);
}

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Listener listener = new Listener () {
		public void handleEvent (Event e) {
			String string = e.type == SWT.KeyDown ? "DOWN:" : "UP  :";
			string += " stateMask=0x" + Integer.toHexString (e.stateMask) + stateMask (e.stateMask) + ",";
			string += " keyCode=0x" + Integer.toHexString (e.keyCode) + " " + keyCode (e.keyCode) + ",";
			string += " character=0x" + Integer.toHexString (e.character) + " " + character (e.character);
			System.out.println (string);
		}
	};
	shell.addListener (SWT.KeyDown, listener);
	shell.addListener (SWT.KeyUp, listener);
	shell.setSize (200, 200);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10168, 1, '[SWT]SnippetSnippet250.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * DateTime example snippet: create a DateTime calendar and a DateTime time.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet250 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new RowLayout ());

	DateTime calendar = new DateTime (shell, SWT.CALENDAR);
	calendar.addSelectionListener (new SelectionAdapter () {
		public void widgetSelected (SelectionEvent e) {
			System.out.println ("calendar date changed");
		}
	});

	DateTime time = new DateTime (shell, SWT.TIME);
	time.addSelectionListener (new SelectionAdapter () {
		public void widgetSelected (SelectionEvent e) {
			System.out.println ("time changed");
		}
	});

	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10169, 1, '[SWT]SnippetSnippet251.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * DateTime example snippet: create a DateTime calendar, date, and time in a dialog.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet251 {

public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());

	Button open = new Button (shell, SWT.PUSH);
	open.setText ("Open Dialog");
	open.addSelectionListener (new SelectionAdapter () {
		public void widgetSelected (SelectionEvent e) {
			final Shell dialog = new Shell (shell, SWT.DIALOG_TRIM);
			dialog.setLayout (new GridLayout (3, false));

			final DateTime calendar = new DateTime (dialog, SWT.CALENDAR | SWT.BORDER);
			final DateTime date = new DateTime (dialog, SWT.DATE | SWT.SHORT);
			final DateTime time = new DateTime (dialog, SWT.TIME | SWT.SHORT);

			new Label (dialog, SWT.NONE);
			new Label (dialog, SWT.NONE);
			Button ok = new Button (dialog, SWT.PUSH);
			ok.setText ("OK");
			ok.setLayoutData(new GridData (SWT.FILL, SWT.CENTER, false, false));
			ok.addSelectionListener (new SelectionAdapter () {
				public void widgetSelected (SelectionEvent e) {
					System.out.println ("Calendar date selected (MM/DD/YYYY) = " + (calendar.getMonth () + 1) + "/" + calendar.getDay () + "/" + calendar.getYear ());
					System.out.println ("Date selected (MM/YYYY) = " + (date.getMonth () + 1) + "/" + date.getYear ());
					System.out.println ("Time selected (HH:MM) = " + time.getHours () + ":" + time.getMinutes ());
					dialog.close ();
				}
			});
			dialog.setDefaultButton (ok);
			dialog.pack ();
			dialog.open ();
		}
	});
	shell.pack ();
	shell.open ();
	
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10170, 1, '[SWT]SnippetSnippet252.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * LineAttributes example snippet: draw 2 polylines with different line attributes.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet252 {
	
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		
		shell.addListener(SWT.Paint, new Listener() {
			public void handleEvent(Event event) {
				GC gc = event.gc;
				
				gc.setLineAttributes(new LineAttributes(10, SWT.CAP_FLAT, SWT.JOIN_MITER, SWT.LINE_SOLID, null, 0, 10));
				gc.drawPolyline(new int[]{50, 100, 50, 20, 60, 30, 50, 45});
				
				gc.setLineAttributes(new LineAttributes(1/2f, SWT.CAP_FLAT, SWT.JOIN_MITER, SWT.LINE_DOT, null, 0, 10));
				gc.drawPolyline(new int[]{100, 100, 100, 20, 110, 30, 100, 45});
			}
		});
		
		shell.setSize(150, 150);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
} 
', now(), now());
insert into SNIPPET values (10171, 1, '[SWT]SnippetSnippet253.java', '/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table example snippet: compute the visible rows in a table
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet253 {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		FillLayout layout = new FillLayout (SWT.VERTICAL);
		shell.setLayout (layout);
		final Table table = new Table (shell, SWT.NONE);
		for (int i=0; i<32; i++) {
			TableItem item = new TableItem (table, SWT.NONE);
			item.setText ("Item " + (i+1) + " is quite long");
		}
		final Button button = new Button (shell, SWT.PUSH);
		button.setText ("Visible Items []");
		button.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				Rectangle rect = table.getClientArea ();
				int itemHeight = table.getItemHeight ();
				int headerHeight = table.getHeaderHeight ();
				int visibleCount = (rect.height - headerHeight + itemHeight - 1) / itemHeight;
				button.setText ("Visible Items [" + visibleCount + "]");
			}
		});
		shell.setSize(200, 250);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
} 
', now(), now());
insert into SNIPPET values (10172, 1, '[SWT]SnippetSnippet256.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * TextLayout example snippet: text with underline and strike through
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet256 {
	
public static void main(String[] args) {
	Display display = new Display();
	final Shell shell = new Shell(display, SWT.SHELL_TRIM | SWT.DOUBLE_BUFFERED);	
	shell.setText("Underline, Strike Out");
	Font font = shell.getFont();
	String text = "Here is some text that is underlined or struck out or both.";
	final TextLayout layout = new TextLayout(display);
	layout.setText(text);
	TextStyle style1 = new TextStyle(font, null, null);
	style1.underline = true;
	layout.setStyle(style1, 26, 35);
	TextStyle style2 = new TextStyle(font, null, null);
	style2.strikeout = true;
	layout.setStyle(style2, 40, 49);
	TextStyle style3 = new TextStyle(font, null, null);
	style3.underline = true;
	style3.strikeout = true;
	layout.setStyle(style3, 54, 57);
	shell.addListener(SWT.Paint, new Listener() {
		public void handleEvent(Event event) {
			Point point = new Point(10, 10);
			int width = shell.getClientArea().width - 2 * point.x;
			layout.setWidth(width);
			layout.draw(event.gc, point.x, point.y);		
		}
	});
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	layout.dispose();		
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10173, 1, '[SWT]SnippetSnippet257.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Drag text selection within a StyledText widget
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */ 
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet257 {

	static String string1 = "A drag source is the provider of data in a Drag and Drop data transfer as well as "+
                           "the originator of the Drag and Drop operation. The data provided by the drag source "+
                           "may be transferred to another location in the same widget, to a different widget "+
                           "within the same application, or to a different application altogether. For example, "+
                           "you can drag text from your application and drop it on an email application, or you "+
                           "could drag an item in a tree and drop it below a different node in the same tree.";

	static String DRAG_START_DATA = "DRAG_START_DATA";
	
public static void main (String [] args) {
	final Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	shell.setSize(100, 300);
	int style = SWT.MULTI | SWT.WRAP | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER;
	final StyledText text = new StyledText(shell, style);
	text.setText(string1);
	final DragSource source = new DragSource(text, DND.DROP_COPY | DND.DROP_MOVE);
	source.setDragSourceEffect(new DragSourceEffect(text) {
		public void dragStart(DragSourceEvent event) {
			event.image = display.getSystemImage(SWT.ICON_WARNING);
		}
	});
	source.setTransfer(new Transfer[] {TextTransfer.getInstance()});
	source.addDragListener(new DragSourceAdapter() {
		Point selection;
		public void dragStart(DragSourceEvent event) {
			selection = text.getSelection();
			event.doit = selection.x != selection.y;
			text.setData(DRAG_START_DATA, selection);
		}
		public void dragSetData(DragSourceEvent e) {
			e.data = text.getText(selection.x, selection.y-1);
		}
		public void dragFinished(DragSourceEvent event) {
			if (event.detail == DND.DROP_MOVE) {
				Point newSelection= text.getSelection();
				int length = selection.y - selection.x;
				int delta = 0;
				if (newSelection.x < selection.x)
					delta = length; 
				text.replaceTextRange(selection.x + delta, length, "");
			}
			selection = null;
			text.setData(DRAG_START_DATA, null);
		}
	});
	
	DropTarget target = new DropTarget(text, DND.DROP_DEFAULT | DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK);
	target.setTransfer(new Transfer[] {TextTransfer.getInstance()});
	target.addDropListener(new DropTargetAdapter() {
		public void dragEnter(DropTargetEvent event) {
			if (event.detail == DND.DROP_DEFAULT) {
				if (text.getData(DRAG_START_DATA) == null)
					event.detail = DND.DROP_COPY;
				else 
					event.detail = DND.DROP_MOVE;
			}
		}
		public void dragOperationChanged(DropTargetEvent event) {
			if (event.detail == DND.DROP_DEFAULT) {
				if (text.getData(DRAG_START_DATA) == null)
					event.detail = DND.DROP_COPY;
				else 
					event.detail = DND.DROP_MOVE;
			}
		}
		public void dragOver(DropTargetEvent event) {
			event.feedback = DND.FEEDBACK_SCROLL | DND.FEEDBACK_SELECT;
		}
		public void drop(DropTargetEvent event) {
			if (event.detail != DND.DROP_NONE) {
				Point selection = (Point) text.getData(DRAG_START_DATA);
				int insertPos = text.getCaretOffset();
				if (event.detail == DND.DROP_MOVE && selection != null && selection.x <= insertPos  && insertPos <= selection.y 
						|| event.detail == DND.DROP_COPY && selection != null && selection.x < insertPos  && insertPos < selection.y) {
					text.setSelection(selection);
					event.detail = DND.DROP_COPY;  // prevent source from deleting selection
				} else {
					String string = (String)event.data;
					text.insert(string);
					if (selection != null)
						text.setSelectionRange(insertPos, string.length());
				}
			}
		}
	});
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

}
', now(), now());
insert into SNIPPET values (10174, 1, '[SWT]SnippetSnippet258.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Create a search text control
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */ 
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.events.*;

public class Snippet258 {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(2, false));
		
		final Text text = new Text(shell, SWT.SEARCH | SWT.CANCEL);
		Image image = null;
		if ((text.getStyle() & SWT.CANCEL) == 0) {
			image = display.getSystemImage(SWT.ICON_ERROR);
			ToolBar toolBar = new ToolBar (shell, SWT.FLAT);
			ToolItem item = new ToolItem (toolBar, SWT.PUSH);
			item.setImage (image);
			item.addSelectionListener(new SelectionAdapter() {
				public void widgetSelected(SelectionEvent e) {
					text.setText("");
					System.out.println("Search cancelled");
				}
			});
		}
		text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		text.setText("Search text");
		text.addSelectionListener(new SelectionAdapter() {
			public void widgetDefaultSelected(SelectionEvent e) {
				if (e.detail == SWT.CANCEL) {
					System.out.println("Search cancelled");
				} else {
					System.out.println("Searching for: " + text.getText() + "...");
				}
			}
		});

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		if (image != null) image.dispose();
		display.dispose();
	}
}', now(), now());
insert into SNIPPET values (10175, 1, '[SWT]SnippetSnippet259.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Detect drag in a custom control
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */ 
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet259 {

static class MyList extends Canvas {
	int selection;
	String [] items = new String [0];
	static final int INSET_X = 2;
	static final int INSET_Y = 2;
	
static int checkStyle (int style) {
	style &= ~(SWT.H_SCROLL | SWT.V_SCROLL);
	style |= SWT.NO_BACKGROUND | SWT.NO_REDRAW_RESIZE;
	return style;
}

public MyList (Composite parent, int style) {
	super (parent, checkStyle (style));
	super.setDragDetect (false);
	addMouseListener (new MouseAdapter () {
		public void mouseDown (MouseEvent event) {
			GC gc = new GC (MyList.this);
			Rectangle client = getClientArea();
			int index = 0, x = client.x + INSET_X, y = client.y + INSET_Y;
			while (index < items.length) {
				Point pt = gc.stringExtent(items [index]);
				Rectangle item = new Rectangle (x, y, pt.x, pt.y);
				if (item.contains (event.x, event.y)) break;
				y += pt.y;
				if (!client.contains (x, y)) return;
				index++;
			}
			gc.dispose ();
			if (index == items.length || !client.contains (x, y)) {
				return;
			}
			selection = index;
			redraw ();
			update ();
			dragDetect (event);
		}
	});
	addPaintListener (new PaintListener () {
		public void paintControl (PaintEvent event) {
			GC gc = event.gc;
			Color foreground = event.display.getSystemColor (SWT.COLOR_LIST_FOREGROUND);
			Color background = event.display.getSystemColor (SWT.COLOR_LIST_BACKGROUND);
			Color selectForeground = event.display.getSystemColor (SWT.COLOR_LIST_SELECTION_TEXT);
			Color selectBackground = event.display.getSystemColor (SWT.COLOR_LIST_SELECTION);
			gc.setForeground (foreground);
			gc.setBackground (background);
			MyList.this.drawBackground (gc, event.x, event.y, event.width, event.height);
			int x = INSET_X, y = INSET_Y;
			for (int i=0; i<items.length; i++) {
				Point pt = gc.stringExtent(items [i]);
				gc.setForeground (i == selection ? selectForeground : foreground);
				gc.setBackground (i == selection ? selectBackground : background);
				gc.drawString (items [i], x, y);
				y += pt.y;
			}
			
		}
	});
}

public Point computeSize (int wHint, int hHint, boolean changed) {
	GC gc = new GC (this);
	int index = 0, width = 0, height = 0;
	while (index < items.length) {
		Point pt = gc.stringExtent(items [index]);
		width = Math.max (width, pt.x);
		height += pt.y;
		index++;
	}
	gc.dispose ();
	width += INSET_X * 2;
	height += INSET_Y * 2;
	Rectangle rect = computeTrim (0, 0, width, height);
	return new Point (rect.width, rect.height);
}

public String [] getItems () {
	checkWidget ();
	return items;
}

public void setItems (String [] items) {
	checkWidget ();
	if (items == null) SWT.error (SWT.ERROR_NULL_ARGUMENT);
	this.items = items;
	redraw ();	
}
}

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	MyList t1 = new MyList (shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	t1.setItems (new String [] {"Some", "items", "in", "the", "list"});
	t1.addDragDetectListener (new DragDetectListener () {
		public void dragDetected (DragDetectEvent event) {
			System.out.println ("Drag started ...");
		}
	});
	MyList t2 = new MyList (shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	t2.setItems (new String [] {"Some", "items", "in", "another", "list"});
	t2.addDragDetectListener (new DragDetectListener () {
		public void dragDetected (DragDetectEvent event) {
			System.out.println ("Drag started ...");
		}
	});
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10176, 1, '[SWT]SnippetSnippet26.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Combo example snippet: create a combo box (non-editable)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet26 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Combo combo = new Combo (shell, SWT.READ_ONLY);
	combo.setItems (new String [] {"A", "B", "C"});
	combo.setSize (200, 200);
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

} 
', now(), now());
insert into SNIPPET values (10177, 1, '[SWT]SnippetSnippet260.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Mozilla in a Browser
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */ 
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.layout.*;

public class Snippet260 {

	public static void main(String [] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setText("Mozilla");
		final Browser browser;
		try {
			browser = new Browser(shell, SWT.MOZILLA);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			return;
		}
		shell.open();
		browser.setUrl("http://mozilla.org");
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
}', now(), now());
insert into SNIPPET values (10178, 1, '[SWT]SnippetSnippet261.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.widgets.*;

/*
 * Open an OLE Excel sheet.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */ 
public class Snippet261 {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Excel Example");
		shell.setLayout(new FillLayout());
		try {
			OleFrame frame = new OleFrame(shell, SWT.NONE);
			new OleClientSite(frame, SWT.NONE, "Excel.Sheet");
			addFileMenu(frame);
		} catch (SWTError e) {
			System.out.println("Unable to open activeX control");
			return;
		}
		shell.setSize(800, 600);
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	static void addFileMenu(OleFrame frame) {
		final Shell shell = frame.getShell();
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		MenuItem fileMenu = new MenuItem(menuBar, SWT.CASCADE);
		fileMenu.setText("&File");
		Menu menuFile = new Menu(fileMenu);
		fileMenu.setMenu(menuFile);
		MenuItem menuFileControl = new MenuItem(menuFile, SWT.CASCADE);
		menuFileControl.setText("Exit");
		menuFileControl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		frame.setFileMenus(new MenuItem[] { fileMenu });
	}
}
', now(), now());
insert into SNIPPET values (10179, 1, '[SWT]SnippetSnippet262.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import java.io.*;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.widgets.*;

/*
 * Open an OLE Word document.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */ 
public class Snippet262 {
	static OleClientSite clientSite;
	static OleFrame frame;
	
	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Word Example");
		shell.setLayout(new FillLayout());
		try {
			frame = new OleFrame(shell, SWT.NONE);
			clientSite = new OleClientSite(frame, SWT.NONE, "Word.Document");
			addFileMenu(frame);
		} catch (SWTError e) {
			System.out.println("Unable to open activeX control");
			return;
		}
		shell.setSize(800, 600);
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	static void addFileMenu(OleFrame frame) {
		final Shell shell = frame.getShell();
		Menu menuBar = shell.getMenuBar();
		if (menuBar == null) {
			menuBar = new Menu(shell, SWT.BAR);
			shell.setMenuBar(menuBar);
		}
		MenuItem fileMenu = new MenuItem(menuBar, SWT.CASCADE);
		fileMenu.setText("&File");
		Menu menuFile = new Menu(fileMenu);
		fileMenu.setMenu(menuFile);
		frame.setFileMenus(new MenuItem[] { fileMenu });

		MenuItem menuFileOpen = new MenuItem(menuFile, SWT.CASCADE);
		menuFileOpen.setText("Open...");
		menuFileOpen.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fileOpen();
			}
		});
		MenuItem menuFileExit = new MenuItem(menuFile, SWT.CASCADE);
		menuFileExit.setText("Exit");
		menuFileExit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
	}

	static void fileOpen() {
		FileDialog dialog = new FileDialog(clientSite.getShell(), SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.doc" });
		String fileName = dialog.open();
		if (fileName != null) {
			clientSite.dispose();
			clientSite = new OleClientSite(frame, SWT.NONE, "Word.Document", new File(fileName));
			clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
		}
	}
}', now(), now());
insert into SNIPPET values (10180, 1, '[SWT]SnippetSnippet263.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.widgets.*;

/*
 * Open an OLE PowerPoint slide.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */ 
public class Snippet263 {
	static OleClientSite clientSite;

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("PowerPoint Example");
		shell.setLayout(new FillLayout());
		try {
			OleFrame frame = new OleFrame(shell, SWT.NONE);
			clientSite  = new OleClientSite(frame, SWT.NONE, "PowerPoint.Slide");
			addFileMenu(frame);
		} catch (SWTError e) {
			System.out.println("Unable to open activeX control");
			return;
		}
		shell.setSize(800, 600);
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	static void addFileMenu(OleFrame frame) {
		final Shell shell = frame.getShell();
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		MenuItem fileMenu = new MenuItem(menuBar, SWT.CASCADE);
		fileMenu.setText("&File");
		Menu menuFile = new Menu(fileMenu);
		fileMenu.setMenu(menuFile);
		MenuItem menuFileControl = new MenuItem(menuFile, SWT.CASCADE);
		menuFileControl.setText("Exit");
		menuFileControl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		frame.setFileMenus(new MenuItem[] { fileMenu });
	}
}', now(), now());
insert into SNIPPET values (10181, 1, '[SWT]SnippetSnippet264.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.widgets.*;

/*
 * Open an OLE Media Player.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */ 
public class Snippet264 {
	static OleClientSite clientSite;

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("Media Player Example");
		shell.setLayout(new FillLayout());
		try {
			OleFrame frame = new OleFrame(shell, SWT.NONE);
			clientSite  = new OleClientSite(frame, SWT.NONE, "MPlayer");
			addFileMenu(frame);
		} catch (SWTError e) {
			System.out.println("Unable to open activeX control");
			return;
		}
		shell.setSize(800, 600);
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
	
	static void addFileMenu(OleFrame frame) {
		final Shell shell = frame.getShell();
		Menu menuBar = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menuBar);
		MenuItem fileMenu = new MenuItem(menuBar, SWT.CASCADE);
		fileMenu.setText("&File");
		Menu menuFile = new Menu(fileMenu);
		fileMenu.setMenu(menuFile);
		MenuItem menuFileControl = new MenuItem(menuFile, SWT.CASCADE);
		menuFileControl.setText("Exit");
		menuFileControl.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
		frame.setFileMenus(new MenuItem[] { fileMenu });
	}
}', now(), now());
insert into SNIPPET values (10182, 1, '[SWT]SnippetSnippet265.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.widgets.*;

/*
 * Open an OLE Windows Media Player.
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */
public class Snippet265 {
	static OleClientSite clientSite;

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setText("Media Player Example");
		shell.setLayout(new FillLayout());
		try {
			OleFrame frame = new OleFrame(shell, SWT.NONE);
			clientSite = new OleClientSite(frame, SWT.NONE, "WMPlayer.OCX");
			clientSite.doVerb(OLE.OLEIVERB_INPLACEACTIVATE);
			addFileMenu(frame);
		} catch (SWTError e) {
			System.out.println("Unable to open activeX control");
			return;
		}
		shell.setSize(800, 600);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

	static void addFileMenu(OleFrame frame) {
		final Shell shell = frame.getShell();
		Menu menuBar = shell.getMenuBar();
		if (menuBar == null) {
			menuBar = new Menu(shell, SWT.BAR);
			shell.setMenuBar(menuBar);
		}
		MenuItem fileMenu = new MenuItem(menuBar, SWT.CASCADE);
		fileMenu.setText("&File");
		Menu menuFile = new Menu(fileMenu);
		fileMenu.setMenu(menuFile);
		frame.setFileMenus(new MenuItem[] { fileMenu });

		MenuItem menuFileOpen = new MenuItem(menuFile, SWT.CASCADE);
		menuFileOpen.setText("Open...");
		menuFileOpen.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				fileOpen();
			}
		});
		MenuItem menuFileExit = new MenuItem(menuFile, SWT.CASCADE);
		menuFileExit.setText("Exit");
		menuFileExit.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				shell.dispose();
			}
		});
	}

	static void fileOpen() {
		FileDialog dialog = new FileDialog(clientSite.getShell(), SWT.OPEN);
		dialog.setFilterExtensions(new String[] { "*.wmv" });
		String filename = dialog.open();
		if (filename != null) {
			OleAutomation player = new OleAutomation(clientSite);
			int playURL[] = player.getIDsOfNames(new String[] { "URL" });
			if (playURL != null) {
				Variant theFile = new Variant(filename);
				player.setProperty(playURL[0], theFile);
			}
			player.dispose();
		}
	}
}', now(), now());
insert into SNIPPET values (10183, 1, '[SWT]SnippetSnippet266.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/*
 * Span and center columns with a GridLayout.
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */
public class Snippet266 {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(2, true));
		
		TabFolder tabFolder = new TabFolder(shell, SWT.NONE);
		tabFolder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		TabItem item = new TabItem(tabFolder, SWT.NONE);
		item.setText("Widget");
		Composite composite = new Composite(tabFolder, SWT.NONE);
		composite.setLayout(new GridLayout());
		Tree tree = new Tree(composite, SWT.BORDER);
		item.setControl(composite);
		tree.setHeaderVisible(true);
		tree.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		TreeColumn column1 = new TreeColumn(tree, SWT.NONE);
		column1.setText("Standard");
		TreeColumn column2 = new TreeColumn(tree, SWT.NONE);
		column2.setText("Widget");
		TreeItem branch = new TreeItem(tree, SWT.NONE);
		branch.setText(new String [] {"Efficient", "Portable"});
		TreeItem leaf = new TreeItem(branch, SWT.NONE);
		leaf.setText(new String [] {"Cross", "Platform"});
		branch.setExpanded(true);
		branch = new TreeItem(tree, SWT.NONE);
		branch.setText(new String [] {"Native", "Controls"});
		leaf = new TreeItem(branch, SWT.NONE);
		leaf.setText(new String [] {"Cross", "Platform"});
		branch = new TreeItem(tree, SWT.NONE);
		branch.setText(new String [] {"Cross", "Platform"});
		column1.pack();
		column2.pack();

		item = new TabItem(tabFolder, SWT.NONE);
		item.setText("Toolkit");
		
		Button button = new Button(shell, SWT.CHECK);
		button.setText("Totally");
		button.setSelection(true);
		button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		
		button = new Button(shell, SWT.PUSH);
		button.setText("Awesome");
		button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false));
		
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}', now(), now());
insert into SNIPPET values (10184, 1, '[SWT]SnippetSnippet267.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.mozilla.interfaces.*;

/*
 * Browser example snippet: Toggle a Mozilla Browser between Design mode and View mode.
 * 
 * IMPORTANT: For this snippet to work properly all of the requirements
 * for using JavaXPCOM in a stand-alone application must be satisfied
 * (see http://www.eclipse.org/swt/faq.php#howusejavaxpcom).
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */
public class Snippet267 {
	static Browser browser;
	public static void main (String [] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(2, true));
		shell.setText("Use Mozillas Design Mode");
		try {
			browser = new Browser(shell, SWT.MOZILLA);
		} catch (SWTError e) {
			System.out.println("Could not instantiate Browser: " + e.getMessage());
			return;
		}
		browser.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, true, 2, 1));

		final Button offButton = new Button(shell, SWT.RADIO);
		offButton.setText("Design Mode Off");
		offButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (!offButton.getSelection()) return;
				setDesignMode("off");
			}
		});
		final Button onButton = new Button(shell, SWT.RADIO);
		onButton.setText("Design Mode On");
		onButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				if (!onButton.getSelection()) return;
				boolean success = setDesignMode("on");
				if (!success) {
					onButton.setSelection(false);
					offButton.setSelection(true);
				}
			}
		});
		offButton.setSelection(true);

		browser.setUrl("http://www.google.com");
		shell.setSize(400,400);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
	public static boolean setDesignMode(String value) {
		nsIWebBrowser webBrowser = (nsIWebBrowser)browser.getWebBrowser();
		if (webBrowser == null) {
			System.out.println("Could not get the nsIWebBrowser from the Browser widget");
			return false;
		}
		nsIDOMWindow window = webBrowser.getContentDOMWindow();
		nsIDOMDocument document = window.getDocument();
		nsIDOMNSHTMLDocument nsDocument = (nsIDOMNSHTMLDocument)document.queryInterface(nsIDOMNSHTMLDocument.NS_IDOMNSHTMLDOCUMENT_IID);
		nsDocument.setDesignMode(value);
		return true;
	}
}
', now(), now());
insert into SNIPPET values (10185, 1, '[SWT]SnippetSnippet268.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
  
/*
 * UI Automation (for testing tools) snippet: post mouse wheel events to a styled text
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.*;

public class Snippet268 {

public static void main(String[] args) {
	final Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	StyledText styledText = new StyledText(shell, SWT.V_SCROLL);
	String multiLineString = "";
	for (int i = 0; i < 200; i++) {
		multiLineString = multiLineString.concat("This is line number " + i + " in the multi-line string.\n");
	}
	styledText.setText(multiLineString);
	shell.setSize(styledText.computeSize(SWT.DEFAULT, 400));
	shell.open();
	styledText.addListener(SWT.MouseWheel, new Listener() {
		public void handleEvent(Event e){
			System.out.println("Mouse Wheel event " + e);
		}
	});
	final Point pt = display.map(shell, null, 50, 50);
	new Thread(){
		Event event;
		public void run() {
			for (int i = 0; i < 50; i++) {
				event = new Event();
				event.type = SWT.MouseWheel;
				event.detail = SWT.SCROLL_LINE;
				event.x = pt.x;
				event.y = pt.y;
				event.count = -2;
				display.post(event);
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
			}
		}	
	}.start();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10186, 1, '[SWT]SnippetSnippet269.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
  
/*
 * Combo example snippet: set the caret position within a Combos text
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;

public class Snippet269 {

public static void main(String[] args) {
	final Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	Combo combo = new Combo (shell, SWT.NONE);
	combo.add ("item");
	combo.select (0);
	shell.pack ();
	shell.open ();
	int stringLength = combo.getText ().length (); 
	combo.setSelection (new Point (stringLength, stringLength));
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10187, 1, '[SWT]SnippetSnippet27.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Shell example snippet: open a shell minimized (iconified)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.widgets.*;

public class Snippet27 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setMinimized (true);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

} 
', now(), now());
insert into SNIPPET values (10188, 1, '[SWT]SnippetSnippet270.java', '/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Browser snippet: bring up a browser with pop-up window support
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.browser.*;

public class Snippet270 {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setText("Main Window");
	shell.setLayout(new FillLayout());
	final Browser browser;
	try {
		browser = new Browser(shell, SWT.NONE);
	} catch (SWTError e) {
		System.out.println("Could not instantiate Browser: " + e.getMessage());
		return;
	}
	initialize(display, browser);
	shell.open();
	browser.setUrl("http://www.eclipse.org");
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
		}
		display.dispose();
	}

/* register WindowEvent listeners */
static void initialize(final Display display, Browser browser) {
	browser.addOpenWindowListener(new OpenWindowListener() {
		public void open(WindowEvent event) {
			if (!event.required) return;	/* only do it if necessary */
			Shell shell = new Shell(display);
			shell.setText("New Window");
			shell.setLayout(new FillLayout());
			Browser browser = new Browser(shell, SWT.NONE);
			initialize(display, browser);
			event.browser = browser;
		}
	});
	browser.addVisibilityWindowListener(new VisibilityWindowListener() {
		public void hide(WindowEvent event) {
			Browser browser = (Browser)event.widget;
			Shell shell = browser.getShell();
			shell.setVisible(false);
		}
		public void show(WindowEvent event) {
			Browser browser = (Browser)event.widget;
			final Shell shell = browser.getShell();
			if (event.location != null) shell.setLocation(event.location);
			if (event.size != null) {
				Point size = event.size;
				shell.setSize(shell.computeSize(size.x, size.y));
			}
			shell.open();
		}
	});
	browser.addCloseWindowListener(new CloseWindowListener() {
		public void close(WindowEvent event) {
			Browser browser = (Browser)event.widget;
			Shell shell = browser.getShell();
			shell.close();
		}
	});
}
}', now(), now());
insert into SNIPPET values (10189, 1, '[SWT]SnippetSnippet271.java', '/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table snippet: specify custom content dimensions in a table with no columns
 * 
 * For a detailed explanation of this snippet see
 * http://www.eclipse.org/articles/Article-CustomDrawingTableAndTreeItems/customDraw.htm#_example1
 *  
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet271 {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setBounds(10,10,200,250);
	final Table table = new Table(shell, SWT.NONE);
	table.setBounds(10,10,150,200);
	table.setLinesVisible(true);
	for (int i = 0; i < 5; i++) {
		new TableItem(table, SWT.NONE).setText("item " + i);
	}

	/*
	 * NOTE: MeasureItem is called repeatedly.  Therefore it is critical
	 * for performance that this method be as efficient as possible.
	 */
	table.addListener(SWT.MeasureItem, new Listener() {
		public void handleEvent(Event event) {
			int clientWidth = table.getClientArea().width;
			event.height = event.gc.getFontMetrics().getHeight() * 2;
			event.width = clientWidth * 2;
		}
	});

	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10190, 1, '[SWT]SnippetSnippet272.java', '/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table snippet: specify custom column widths when a column is packed
 *  
 * For a detailed explanation of this snippet see
 * http://www.eclipse.org/articles/Article-CustomDrawingTableAndTreeItems/customDraw.htm#_example2
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet272 {

public static void main(String[] args) {
	Display display = new Display(); 
	Shell shell = new Shell(display);
	shell.setBounds(10,10,400,200);
	Table table = new Table(shell, SWT.NONE);
	table.setBounds(10,10,350,150);
	table.setHeaderVisible(true);
	table.setLinesVisible(true);
	final TableColumn column0 = new TableColumn(table, SWT.NONE);
	column0.setWidth(100);
	final TableColumn column1 = new TableColumn(table, SWT.NONE);
	column1.setWidth(100);
	column0.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event event) {
			column0.pack();
		}
	});
	column1.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event event) {
			column1.pack();
		}
	});
	for (int i = 0; i < 5; i++) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(0, "item " + i + " col 0");
		item.setText(1, "item " + i + " col 1");
	}

	/*
	 * NOTE: MeasureItem is called repeatedly.  Therefore it is critical
	 * for performance that this method be as efficient as possible.
	 */
	table.addListener(SWT.MeasureItem, new Listener() {
		public void handleEvent(Event event) {
			event.width *= 2;
		}
	});

	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10191, 1, '[SWT]SnippetSnippet273.java', '/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table snippet: modify the clipping of custom background paints
 * 
 * For a detailed explanation of this snippet see
 * http://www.eclipse.org/articles/Article-CustomDrawingTableAndTreeItems/customDraw.htm#_example4
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.2
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet273 {

public static void main(String[] args) {
	final String[] MONTHS = {
		"Jan", "Feb", "Mar", "Apr", "May", "Jun",
		"Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
	};
	final int[] HIGHS = {-7, -4, 1, 11, 18, 24, 26, 25, 20, 13, 5, -4};
	final int[] LOWS = {-15, -13, -7, 1, 7, 13, 15, 14, 10, 4, -2, -11};
	final int SCALE_MIN = -30; final int SCALE_MAX = 30;
	final int SCALE_RANGE = Math.abs(SCALE_MIN - SCALE_MAX);

	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setBounds(10,10,400,350);
	shell.setText("Ottawa Average Daily Temperature Ranges");
	final Color blue = display.getSystemColor(SWT.COLOR_BLUE);
	final Color white = display.getSystemColor(SWT.COLOR_WHITE);
	final Color red = display.getSystemColor(SWT.COLOR_RED);
	// final Image parliamentImage = new Image(display, "./parliament.jpg");
	final Table table = new Table(shell, SWT.NONE);
	table.setBounds(10,10,350,300);
	// table.setBackgroundImage(parliamentImage);
	for (int i = 0; i < 12; i++) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(MONTHS[i] + " (" + LOWS[i] + "C..." + HIGHS[i] + "C)");
	}
	final int clientWidth = table.getClientArea().width;

	/*
	 * NOTE: MeasureItem and EraseItem are called repeatedly. Therefore it is
	 * critical for performance that these methods be as efficient as possible.
	 */
	table.addListener(SWT.MeasureItem, new Listener() {
		public void handleEvent(Event event) {
			int itemIndex = table.indexOf((TableItem)event.item);
			int rightX = (HIGHS[itemIndex] - SCALE_MIN) * clientWidth / SCALE_RANGE;
			event.width = rightX;
		}
	});
	table.addListener(SWT.EraseItem, new Listener() {
		public void handleEvent(Event event) {
			int itemIndex = table.indexOf((TableItem)event.item);
			int leftX = (LOWS[itemIndex] - SCALE_MIN) * clientWidth / SCALE_RANGE;
			int rightX = (HIGHS[itemIndex] - SCALE_MIN) * clientWidth / SCALE_RANGE;
			GC gc = event.gc;
			Rectangle clipping = gc.getClipping();
			clipping.x = leftX;
			clipping.width = rightX - leftX;
			gc.setClipping(clipping);
			Color oldForeground = gc.getForeground();
			Color oldBackground = gc.getBackground();
			gc.setForeground(blue);
			gc.setBackground(white);
			gc.fillGradientRectangle(event.x, event.y, event.width / 2, event.height, false);
			gc.setForeground(white);
			gc.setBackground(red);
			gc.fillGradientRectangle(
				event.x + event.width / 2, event.y, event.width / 2, event.height, false);
			gc.setForeground(oldForeground);
			gc.setBackground(oldBackground);
			event.detail &= ~SWT.BACKGROUND;
			event.detail &= ~SWT.HOT;
		}
	});

	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	// parliamentImage.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10192, 1, '[SWT]SnippetSnippet274.java', '/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tree snippet: implement standard tree check box behavior (SWT.CHECK)
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet274 {
	
	static void checkPath(TreeItem item, boolean checked, boolean grayed) {
	    if (item == null) return;
	    if (grayed) {
	        checked = true;
	    } else {
	        int index = 0;
	        TreeItem[] items = item.getItems();
	        while (index < items.length) {
	            TreeItem child = items[index];
	            if (child.getGrayed() || checked != child.getChecked()) {
	                checked = grayed = true;
	                break;
	            }
	            index++;
	        }
	    }
	    item.setChecked(checked);
	    item.setGrayed(grayed);
	    checkPath(item.getParentItem(), checked, grayed);
	}

	static void checkItems(TreeItem item, boolean checked) {
	    item.setGrayed(false);
	    item.setChecked(checked);
	    TreeItem[] items = item.getItems();
	    for (int i = 0; i < items.length; i++) {
	        checkItems(items[i], checked);
	    }
	}

	public static void main(String[] args) {
	    Display display = new Display();
	    Shell shell = new Shell(display);
	    Tree tree = new Tree(shell, SWT.BORDER | SWT.CHECK);
	    tree.addListener(SWT.Selection, new Listener() {
	        public void handleEvent(Event event) {
	            if (event.detail == SWT.CHECK) {
	                TreeItem item = (TreeItem) event.item;
	                boolean checked = item.getChecked();
	                checkItems(item, checked);
	                checkPath(item.getParentItem(), checked, false);
	            }
	        }
	    });
	    for (int i = 0; i < 4; i++) {
	        TreeItem itemI = new TreeItem(tree, SWT.NULL);
	        itemI.setText("Item " + i);
	        for (int j = 0; j < 4; j++) {
	            TreeItem itemJ = new TreeItem(itemI, SWT.NULL);
	            itemJ.setText("Item " + i + " " + j);
	            for (int k = 0; k < 4; k++) {
	                TreeItem itemK = new TreeItem(itemJ, SWT.NULL);
	                itemK.setText("Item " + i + " " + j + " " + k);
	            }
	        }
	    }
	    tree.setSize(200, 200);
	    shell.pack();
	    shell.open();
	    while (!shell.isDisposed()) {
	        if (!display.readAndDispatch()) display.sleep();
	    }
	    display.dispose();
	}

}
', now(), now());
insert into SNIPPET values (10193, 1, '[SWT]SnippetSnippet275.java', '/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Canvas snippet: update a portion of a Canvas frequently
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet275 {

static String value;
public static void main (String[] args) {
	final int INTERVAL = 888;
	final Display display = new Display ();
	final Image image = new Image (display, 750, 750);
	GC gc = new GC (image);
	gc.setBackground (display.getSystemColor (SWT.COLOR_RED));
	gc.fillRectangle (image.getBounds ());
	gc.dispose ();

	Shell shell = new Shell (display);
	shell.setBounds (10, 10, 790, 790);
	final Canvas canvas = new Canvas (shell, SWT.NONE);
	canvas.setBounds (10, 10, 750, 750);
	canvas.addListener (SWT.Paint, new Listener () {
		public void handleEvent (Event event) {
			value = String.valueOf (System.currentTimeMillis ());
			event.gc.drawImage (image, 0, 0);
			event.gc.drawString (value, 10, 10, true);
		}
	});
	display.timerExec (INTERVAL, new Runnable () {
		public void run () {
			if (canvas.isDisposed ()) return;
			// canvas.redraw (); // <-- bad, damages more than is needed
			GC gc = new GC (canvas);
			Point extent = gc.stringExtent (value + 0);
			gc.dispose ();
			canvas.redraw (10, 10, extent.x, extent.y, false);
			display.timerExec (INTERVAL, this);
		}
	});
	shell.open ();
	while (!shell.isDisposed ()){
		if (!display.readAndDispatch ()) display.sleep ();
	}
	image.dispose ();
	display.dispose ();
}

}
', now(), now());
insert into SNIPPET values (10194, 1, '[SWT]SnippetSnippet276.java', '/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Display snippet: map from control-relative to display-relative coordinates
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet276 {

public static void main (String[] args) {
	final Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setBounds (200, 200, 400, 400);
	Label label = new Label (shell, SWT.NONE);
	label.setText ("click in shell to print display-relative coordinate");
	Listener listener = new Listener () {
		public void handleEvent (Event event) {
			Point point = new Point (event.x, event.y);
			System.out.println (display.map ((Control)event.widget, null, point));
		}
	};
	shell.addListener (SWT.MouseDown, listener);
	label.addListener (SWT.MouseDown, listener);
	label.pack ();
	shell.open ();
	while (!shell.isDisposed ()){
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

}
', now(), now());
insert into SNIPPET values (10195, 1, '[SWT]SnippetSnippet277.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Browser example snippet: Implement a custom download handler for a Mozilla Browser.
 * 
 * IMPORTANT: For this snippet to work properly all of the requirements
 * for using JavaXPCOM in a stand-alone application must be satisfied
 * (see http://www.eclipse.org/swt/faq.php#howusejavaxpcom).
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.3
 */
import org.eclipse.swt.*;
import org.eclipse.swt.browser.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;
import org.mozilla.interfaces.*;
import org.mozilla.xpcom.Mozilla;

public class Snippet277 {
	static Shell shell;
	static Table table;

	public static void main (String [] args) {
		Display display = new Display ();
		shell = new Shell (display);
		shell.setLayout(new GridLayout ());
		shell.setText ("Custom Download Handler");

		Browser browser;
		try {
			browser = new Browser (shell, SWT.MOZILLA);
		} catch (SWTError e) {
			System.out.println ("Could not instantiate Browser: " + e.getMessage ());
			return;
		}
		GridData data = new GridData (GridData.FILL_BOTH);
		data.minimumHeight = 800;
		data.minimumWidth = 800;
		browser.setLayoutData (data);

		table = new Table (shell, SWT.NONE);
		table.setForeground (display.getSystemColor (SWT.COLOR_RED));
		data = new GridData (GridData.FILL_HORIZONTAL);
		data.exclude = true;
		table.setLayoutData (data);
		new TableColumn (table, SWT.NONE);
		new TableColumn (table, SWT.NONE);

		nsIComponentRegistrar registrar = Mozilla.getInstance ().getComponentRegistrar ();
		String NS_DOWNLOAD_CID = "e3fa9D0a-1dd1-11b2-bdef-8c720b597445";
		String NS_TRANSFER_CONTRACTID = "@mozilla.org/transfer;1";
		registrar.registerFactory (NS_DOWNLOAD_CID, "Transfer", NS_TRANSFER_CONTRACTID, new nsIFactory () {
			public nsISupports queryInterface (String uuid) {
				if (uuid.equals (nsIFactory.NS_IFACTORY_IID) ||
					uuid.equals (nsIFactory.NS_ISUPPORTS_IID)) return this;
				return null;
			}
			public nsISupports createInstance (nsISupports outer, String iid) {
				return createTransfer ();
			}
			public void lockFactory (boolean lock) {}
		});

		browser.setUrl ("http://www.eclipse.org/downloads");
		shell.pack ();
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}

	static nsITransfer createTransfer () {
		/* nsITransfer is documented at http://www.xulplanet.com/references/xpcomref/ifaces/nsITransfer.html */
		return new nsITransfer () {
			public nsISupports queryInterface (String uuid) {
				if (uuid.equals (nsITransfer.NS_ITRANSFER_IID) ||
					uuid.equals (nsITransfer.NS_IWEBPROGRESSLISTENER2_IID) ||
					uuid.equals (nsITransfer.NS_IWEBPROGRESSLISTENER_IID) ||
					uuid.equals (nsITransfer.NS_ISUPPORTS_IID)) return this;
				return null;
			}
			public void onStateChange (nsIWebProgress webProgress, nsIRequest request, long stateFlags, long status) {
				if ((stateFlags & nsIWebProgressListener.STATE_STOP) != 0) {
					removeFromTable ();
				}
			}
			public void onProgressChange64 (nsIWebProgress webProgress, nsIRequest request, long curSelfProgress, long maxSelfProgress, long curTotalProgress, long maxTotalProgress) {
				long currentKBytes = curTotalProgress / 1024;
				long totalKBytes = maxTotalProgress / 1024;
				tableItem.setText (1, baseString + " (" + currentKBytes + "/" + totalKBytes + ")");
				table.getColumn (1).pack ();
			}
			public void init (nsIURI source, nsIURI target, String displayName, nsIMIMEInfo MIMEInfo, double startTime, nsILocalFile tempFile, final nsICancelable cancelable) {
				tableItem = new TableItem (table, SWT.NONE);
				button = new Button (table, SWT.PUSH);
				button.setText ("Cancel");
				button.pack ();
				button.addListener (SWT.Selection, new Listener () {
					public void handleEvent (Event event) {
						cancelable.cancel (Mozilla.NS_ERROR_ABORT);
						removeFromTable ();
					}
				});
				TableEditor editor = new TableEditor (table);
				editor.setEditor (button, tableItem, 0);
				editor.minimumWidth = button.computeSize (SWT.DEFAULT, SWT.DEFAULT).x;
				baseString = "Downloading to " + target.getPath ();
				tableItem.setText (1, baseString);
				if (table.getItemCount () == 1) {
					((GridData)table.getLayoutData ()).exclude = false;	/* show the table */
					table.getColumn (0).setWidth (editor.minimumWidth);
				}
				table.getColumn (1).pack ();
				table.getShell ().layout ();
			}
			public void onStatusChange (nsIWebProgress webProgress, nsIRequest request, long status, String message) {}
			public void onSecurityChange (nsIWebProgress webProgress, nsIRequest request, long state) {}
			public void onProgressChange (nsIWebProgress webProgress, nsIRequest request, int curSelfProgress, int maxSelfProgress, int curTotalProgress, int maxTotalProgress) {}
			public void onLocationChange (nsIWebProgress webProgress, nsIRequest request, nsIURI location) {}

			/* the following are not part of the nsITransfer interface but are here for the snippets convenience */
			Button button;
			TableItem tableItem;
			String baseString;

			void removeFromTable () {
				tableItem.dispose ();
				button.dispose ();
				if (table.getItemCount () == 0) {
					((GridData)table.getLayoutData ()).exclude = true;	/* hide the table */
				}
				table.getColumn (1).pack ();
				table.getShell ().layout ();
			}
		};
	}
}
', now(), now());
insert into SNIPPET values (10196, 1, '[SWT]SnippetSnippet278.java', '/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tool tip snippet: show a Labels tool tip iff its not fully visible
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet278 {

public static void main (String [] args) {
	final Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setBounds (10, 10, 300, 100);
	shell.setLayout (new FillLayout ());
	final Label label = new Label (shell, SWT.NONE);
	label.setText ("resize the Shell then hover over this Label");
	label.addListener (SWT.MouseEnter, new Listener () {
		public void handleEvent (Event event) {
			Point requiredSize = label.computeSize (SWT.DEFAULT, SWT.DEFAULT);
			Point labelSize = label.getSize ();
			boolean fullyVisible = requiredSize.x <= labelSize.x && requiredSize.y <= labelSize.y;
			System.out.println ("Label is fully visible: " + fullyVisible);
			label.setToolTipText (fullyVisible ? null : label.getText ());
		}
	});
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

}
', now(), now());
insert into SNIPPET values (10197, 1, '[SWT]SnippetSnippet279.java', '/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * draw a reflection of an image
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet279 {
	public static void main (String [] args) {
		Display display = new Display ();
		Shell shell = new Shell (display, SWT.SHELL_TRIM | SWT.DOUBLE_BUFFERED);
		shell.setLayout(new FillLayout ());
		final Image image = display.getSystemImage (SWT.ICON_QUESTION);
		shell.addListener (SWT.Paint, new Listener () {
			public void handleEvent (Event e) {
				Rectangle rect = image.getBounds ();
				GC gc = e.gc;
				int x = 10, y = 10;
				gc.drawImage (image, x, y);
				Transform tr = new Transform (e.display);
				tr.setElements (1, 0, 0, -1, 1, 2*(y+rect.height));
				gc.setTransform (tr);
				gc.drawImage (image, x, y);
				gc.setTransform (null);
				Color background = gc.getBackground ();
				Pattern p = new Pattern (e.display, x, y+rect.height, x, y+(2*rect.height), background, 0, background, 255);
				gc.setBackgroundPattern (p);
				gc.fillRectangle (x, y+rect.height, rect.width, rect.height);
				p.dispose ();
				tr.dispose ();
			}
		});
		shell.setSize (600, 400);
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ())
				display.sleep ();
		}
		display.dispose ();
	}
	
}

', now(), now());
insert into SNIPPET values (10198, 1, '[SWT]SnippetSnippet28.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Shell example snippet: open a shell maximized (full screen)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.widgets.*;

public class Snippet28 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setMaximized (true);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

} 
', now(), now());
insert into SNIPPET values (10199, 1, '[SWT]SnippetSnippet280.java', '/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * draw a multi-gradient (without advanced graphics)
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet280 {

public static void main (String [] args) {
	final Display display = new Display ();
	final Shell shell = new Shell (display, SWT.SHELL_TRIM | SWT.NO_BACKGROUND);
	shell.setLayout(new FillLayout ());
	shell.addListener (SWT.Paint, new Listener () {
		public void handleEvent (Event e) {
			GC gc = e.gc;
			Rectangle rect = shell.getClientArea ();
			Color color1 = new Color (display, 234, 246, 253);
			Color color2 = new Color (display, 217, 240, 252);
			gc.setForeground(color1);
			gc.setBackground(color2);
			gc.fillGradientRectangle (rect.x, rect.y, rect.width, rect.height / 2, true);
			color1.dispose ();
			color2.dispose ();
			Color color3 = new Color (display, 190, 230, 253);
			Color color4 = new Color (display, 167, 217, 245);
			gc.setForeground(color3);
			gc.setBackground(color4);
			gc.fillGradientRectangle (rect.x, rect.y + (rect.height / 2), rect.width, rect.height / 2 + 1, true);
			color3.dispose ();
			color4.dispose ();
			
		}
	});
	shell.setSize (200, 64);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ())
			display.sleep ();
	}
	display.dispose ();
}

}
', now(), now());
insert into SNIPPET values (10200, 1, '[SWT]SnippetSnippet281.java', '/*******************************************************************************
 * Copyright (c) 2000, 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * draw a multi-gradient
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet281 {

public static void main (String [] args) {
	final Display display = new Display ();
	final Shell shell = new Shell (display, SWT.SHELL_TRIM | SWT.NO_BACKGROUND);
	shell.setLayout(new FillLayout ());
	shell.addListener (SWT.Paint, new Listener () {
		public void handleEvent (Event e) {
			GC gc = e.gc;
			Rectangle rect = shell.getClientArea ();
			Color color1 = new Color (display, 234, 246, 253);
			Color color2 = new Color (display, 217, 240, 252);
			Color color3 = new Color (display, 190, 230, 253);
			Color color4 = new Color (display, 167, 217, 245);
			Pattern p1 = new Pattern (display, 0, 0, 0, rect.height / 2, color1, color2);
			gc.setBackgroundPattern (p1);
			gc.fillRectangle (rect.x, rect.y, rect.width, rect.height / 2);
			Pattern p2 = new Pattern (display, 0, rect.height / 2, 0, rect.height, color3, color4);
			gc.setBackgroundPattern (p2);
			gc.fillRectangle (rect.x, rect.y + (rect.height / 2), rect.width, rect.height / 2 + 1);
			p1.dispose ();
			p2.dispose ();
			color1.dispose ();
			color2.dispose ();
			color3.dispose ();
			color4.dispose ();
		}
	});
	shell.setSize (200, 64);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ())
			display.sleep ();
	}
	display.dispose ();
}

}
', now(), now());
insert into SNIPPET values (10201, 1, '[SWT]SnippetSnippet282.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Copy/paste image to/from clipboard.
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet282 {

	public static void main(String[] args) {
		final Display display = new Display();
		final Clipboard clipboard = new Clipboard(display);
		final Shell shell = new Shell(display, SWT.SHELL_TRIM);
		shell.setLayout(new GridLayout());
		shell.setText("Clipboard ImageTransfer");

	    final Button imageButton = new Button(shell, SWT.NONE );
	    GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
	    gd.minimumHeight = 400;
	    gd.minimumWidth = 600;
	    imageButton.setLayoutData(gd);

		Composite buttons = new Composite(shell, SWT.NONE);
		buttons.setLayout(new GridLayout(4, true));
		Button button = new Button(buttons, SWT.PUSH);
		button.setText("Open");
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				FileDialog dialog = new FileDialog (shell, SWT.OPEN);
				dialog.setText("Open an image file or cancel");
				String string = dialog.open ();
				if (string != null) {
					Image image = imageButton.getImage();
					if (image != null) image.dispose();
					image = new Image(display, string);
					imageButton.setImage(image);
				}
			}
		});

		button = new Button(buttons, SWT.PUSH);
		button.setText("Copy");
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				Image image = imageButton.getImage();
				if (image != null) {
					ImageTransfer transfer = ImageTransfer.getInstance();
					clipboard.setContents(new Object[]{image.getImageData()}, new Transfer[]{transfer});
				}
			}
		});

		button = new Button(buttons, SWT.PUSH);
		button.setText("Paste");
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				ImageTransfer transfer = ImageTransfer.getInstance();
				ImageData imageData = (ImageData)clipboard.getContents(transfer);
				if (imageData != null) {
					imageButton.setText("");
					Image image = imageButton.getImage();
					if (image != null) image.dispose();
					image = new Image(display, imageData);
					imageButton.setImage(image);
				} else {
					imageButton.setText("No image");
				}
			}
		});
		
		button = new Button(buttons, SWT.PUSH);
		button.setText("Clear");
		button.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event event) {
				imageButton.setText("");
				Image image = imageButton.getImage();
				if (image != null) image.dispose();
				imageButton.setImage(null);
			}
		});
		
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
}
', now(), now());
insert into SNIPPET values (10202, 1, '[SWT]SnippetSnippet283.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table example snippet: Draw left aligned icon, text and selection
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet283 {
	public static void main(String[] args) {
		Display display = new Display();
		Image image = new Image (display, Snippet283.class.getResourceAsStream("eclipse.png"));
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		final Table table = new Table(shell, SWT.FULL_SELECTION);
		for (int i = 0; i < 8; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			item.setText ("Item " + i + " with long text that scrolls.");
			if (i % 2 == 1) item.setImage (image);
		}
		table.addListener(SWT.MouseDown, new Listener() {
			public void handleEvent(Event event) {
				Rectangle rect = table.getClientArea ();
				Point point = new Point (event.x, event.y);
				if (table.getItem(point) != null) return;
				for (int i=table.getTopIndex (); i<table.getItemCount(); i++) {
					TableItem item = table.getItem (i);
					Rectangle itemRect = item.getBounds ();
					if (!itemRect.intersects (rect)) return;
					itemRect.x = rect.x;
					itemRect.width = rect.width;
					if (itemRect.contains (point)) {
						table.setSelection (item);
						Event selectionEvent = new Event ();
						selectionEvent.item = item;
						table.notifyListeners(SWT.Selection, selectionEvent);
						return;
					}
				}
			}
		});
		/*
		 * NOTE: MeasureItem, PaintItem and EraseItem are called repeatedly.
		 * Therefore, it is critical for performance that these methods be
		 * as efficient as possible.
		 */
		table.addListener(SWT.EraseItem, new Listener() {
			public void handleEvent(Event event) {
				event.detail &= ~SWT.FOREGROUND;
				String osName = System.getProperty("os.name");
				if (osName != null && osName.indexOf ("Windows") != -1) {
					if (osName.indexOf ("Vista") != -1 || osName.indexOf ("unknown") != -1) {
						return;
					}
				}
				event.detail &= ~(SWT.FOREGROUND | SWT.SELECTED | SWT.HOT | SWT.FOCUSED);
				GC gc = event.gc;
				TableItem item = (TableItem)event.item;
				Rectangle rect = table.getClientArea ();
				Rectangle itemRect = item.getBounds ();
				itemRect.x = rect.x;
				itemRect.width = rect.width;
				gc.setClipping ((Rectangle) null);
				gc.fillRectangle (itemRect);
			}
		});
		table.addListener(SWT.PaintItem, new Listener() {
			public void handleEvent(Event event) {
				TableItem item = (TableItem)event.item;
				GC gc = event.gc;
				Image image = item.getImage (0);
				String text = item.getText (0);
				Point textExtent = gc.stringExtent (text);
				Rectangle imageRect = item.getImageBounds(0);
				Rectangle textRect = item.getTextBounds (0);
				int textY = textRect.y + Math.max (0, (textRect.height - textExtent.y) / 2);
				if (image == null) {
					gc.drawString(text, imageRect.x, textY, true);
				} else {
					Rectangle imageExtent = image.getBounds ();
					int imageY = imageRect.y + Math.max (0, (imageRect.height - imageExtent.height) / 2);
					gc.drawImage (image, imageRect.x, imageY);
					gc.drawString (text, textRect.x, textY, true);
				}
			}
		});
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10203, 1, '[SWT]SnippetSnippet284.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Drag and Drop example snippet: drag a URL between two labels.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet284 {

public static void main (String [] args) {
	
	Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setText("URLTransfer");
	shell.setLayout(new FillLayout());
	final Label label1 = new Label (shell, SWT.BORDER);
	label1.setText ("http://www.eclipse.org");
	final Label label2 = new Label (shell, SWT.BORDER);
	setDragSource (label1);
	setDropTarget (label2);
	shell.setSize(600, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
public static void setDragSource (final Label label) {
	int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
	final DragSource source = new DragSource (label, operations);
	source.setTransfer(new Transfer[] {URLTransfer.getInstance()});
	source.addDragListener (new DragSourceListener () {
		public void dragStart(DragSourceEvent e) {
		}
		public void dragSetData(DragSourceEvent e) {
			e.data = label.getText();
		}
		public void dragFinished(DragSourceEvent event) {
		}
	});
}

public static void setDropTarget (final Label label) {
	int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
	DropTarget target = new DropTarget(label, operations);
	target.setTransfer(new Transfer[] {URLTransfer.getInstance()});
	target.addDropListener (new DropTargetAdapter() {
		public void dragEnter(DropTargetEvent e) {
			if (e.detail == DND.DROP_NONE)
				e.detail = DND.DROP_LINK;
		}
		public void dragOperationChanged(DropTargetEvent e) {
			if (e.detail == DND.DROP_NONE)
				e.detail = DND.DROP_LINK;
		}
		public void drop(DropTargetEvent event) {
			if (event.data == null) {
				event.detail = DND.DROP_NONE;
				return;
			}
			label.setText(((String) event.data));
		}
	});
}
}
', now(), now());
insert into SNIPPET values (10204, 1, '[SWT]SnippetSnippet285.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * create a circular shell from a path.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet285 {
	static void loadPath(Region region, float[] points, byte[] types) {
		int start = 0, end = 0;
		for (int i = 0; i < types.length; i++) {
			switch (types[i]) {
				case SWT.PATH_MOVE_TO: {
					if (start != end) {
						int n = 0;
						int[] temp = new int[end - start];
						for (int k = start; k < end; k++) {
							temp[n++] = Math.round(points[k]);
						}
						region.add(temp);
					}
					start = end;
					end += 2;
					break;
				}
				case SWT.PATH_LINE_TO: {
					end += 2;
					break;
				}
				case SWT.PATH_CLOSE: {
					if (start != end) {
						int n = 0;
						int[] temp = new int[end - start];
						for (int k = start; k < end; k++) {
							temp[n++] = Math.round(points[k]);
						}
						region.add(temp);
					}
					start = end;
					break;
				}
			}
		}
	}
	public static void main(String[] args) {
		int width = 250, height = 250;
		final Display display = new Display();
		final Shell shell = new Shell(display, SWT.NO_TRIM);
		final Path path = new Path(display);
		path.addArc(0, 0, width, height, 0, 360);
		Path path2 = new Path(display, path, 0.1f);
		path.dispose();
		PathData data = path2.getPathData();
		path2.dispose();
		Region region = new Region(display);
		loadPath(region, data.points, data.types);
		shell.setRegion(region);
		Listener listener = new Listener() {
			public void handleEvent(Event event) {
				switch (event.type) {
					case SWT.MouseDown: {
						shell.dispose();
						break;
					}
					case SWT.Paint: {
						GC gc = event.gc;
						Rectangle rect = shell.getClientArea();
						Pattern pattern = new Pattern(display, rect.x, rect.y
								+ rect.height, rect.x + rect.width, rect.y, display
								.getSystemColor(SWT.COLOR_BLUE), display
								.getSystemColor(SWT.COLOR_WHITE));
						gc.setBackgroundPattern(pattern);
						gc.fillRectangle(rect);
						pattern.dispose();
						break;
					}
				}
			}
		};
		shell.addListener(SWT.MouseDown, listener);
		shell.addListener(SWT.Paint, listener);
		shell.setSize(width, height);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		region.dispose();
		display.dispose();
	}
	
}
', now(), now());
insert into SNIPPET values (10205, 1, '[SWT]SnippetSnippet286.java', '/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * use a menu items armListener to update a status line.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet286 {

	public static void main(java.lang.String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		
		Canvas blankCanvas = new Canvas(shell, SWT.BORDER);
		blankCanvas.setLayoutData(new GridData(200, 200));
		final Label statusLine = new Label(shell, SWT.NONE);
		statusLine.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		Menu bar = new Menu (shell, SWT.BAR);
		shell.setMenuBar (bar);
		
		MenuItem menuItem = new MenuItem (bar, SWT.CASCADE);
		menuItem.setText ("Test");
		Menu menu = new Menu(bar);
		menuItem.setMenu (menu);
		
		for (int i = 0; i < 5; i++) {
			MenuItem item = new MenuItem (menu, SWT.PUSH);
			item.setText ("Item " + i);
			item.addArmListener(new ArmListener() {
				public void widgetArmed(ArmEvent e) {
					statusLine.setText(((MenuItem)e.getSource()).getText());
				}
			});
		}
		
		shell.pack();
		shell.open();
		
		while(!shell.isDisposed()) {
			if(!display.readAndDispatch()) display.sleep();
		}
	}
}', now(), now());
insert into SNIPPET values (10206, 1, '[SWT]SnippetSnippet287.java', '/*******************************************************************************
 * Copyright (c) 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tree example snippet: search for a string in a Tree recursively
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet287 {

static Tree tree;
public static void main(String[] args) {
	final String SEARCH_STRING = "4b";

	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setBounds (10,10,300,300);
	shell.setLayout (new GridLayout ());

	/* create the Tree */
	tree = new Tree (shell, SWT.FULL_SELECTION);
	tree.setLinesVisible (true);
	tree.setLayoutData (new GridData (GridData.FILL_BOTH));
	for (int i = 0; i < 3; i++) {
		new TreeColumn (tree, SWT.NONE).setWidth (90);
	}
	int index = 0;
	for (int i = 0; i < 3; i++) {
		TreeItem item = createItem (null, index++);
		for (int j = 0; j < i; j++) {
			item = createItem (item, index++);
		}
	}

	Button button = new Button (shell, SWT.PUSH);
	button.setText ("Find " + SEARCH_STRING + "");
	button.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event event) {
			int itemCount = tree.getItemCount ();
			for (int i = 0; i < itemCount; i++) {
				TreeItem item = tree.getItem (i);
				boolean success = find (item, SEARCH_STRING);
				if (success) {
					System.out.println ("Found it");
					return;
				}
			}
			System.out.println ("Did not find it");
		}
	});
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

/* for creating sample Tree */
static TreeItem createItem (TreeItem parent, int itemIndex) {
	TreeItem newItem = null;
	if (parent == null) {	/* root level item */
		newItem = new TreeItem (tree, SWT.NONE);
	} else {
		newItem = new TreeItem (parent, SWT.NONE);
	}
	String indexString = String.valueOf (itemIndex); 
	newItem.setText(new String[] {
		indexString + a, indexString + b, indexString + c});
	return newItem;
}

/* recursive find */
public static boolean find (TreeItem item, String searchString) {
	/* check this item */
	for (int i = 0; i < tree.getColumnCount (); i++) {
		String contents = item.getText (i);
		if ((contents.toUpperCase ().indexOf (searchString.toUpperCase ())) != -1) {
			tree.setSelection (item);
			return true;
		}
	}

	if (!item.getExpanded ()) return false; /* dont check child items */

	/* check child items */
	int childCount = item.getItemCount ();
	for (int i = 0; i < childCount; i++) {
		TreeItem child = item.getItem (i);
		boolean success = find (child, searchString);
		if (success) return true;
	}

	return false;
}
}
', now(), now());
insert into SNIPPET values (10207, 1, '[SWT]SnippetSnippet288.java', '/*******************************************************************************
 * Copyright (c) 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Create a ToolBar containing animated GIFs
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import java.io.File;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet288 {
	
	static Display display;
	static Shell shell;
	static GC shellGC;
	static Color shellBackground;
	static ImageLoader[] loader;
	static ImageData[][] imageDataArray;
	static Thread animateThread[];
	static Image[][] image;
	private static ToolItem item[];
	static final boolean useGIFBackground = false;

	public static void main (String [] args) {
		display = new Display();
		Shell shell = new Shell (display);
		shellBackground = shell.getBackground();
		FileDialog dialog = new FileDialog(shell, SWT.OPEN | SWT.MULTI);
		dialog.setText("Select Multiple Animated GIFs");
		dialog.setFilterExtensions(new String[] {"*.gif"});
		String filename = dialog.open();
		String filenames[] = dialog.getFileNames();
		int numToolBarItems = filenames.length;
		if (numToolBarItems > 0) {
			try {
				loadAllImages(new File(filename).getParent(), filenames);
			} catch (SWTException e) {
				System.err.println("There was an error loading an image.");
				e.printStackTrace();
			}
			ToolBar toolBar = new ToolBar (shell, SWT.FLAT | SWT.BORDER | SWT.WRAP);
			item = new ToolItem[numToolBarItems];
			for (int i = 0; i < numToolBarItems; i++) {
				item[i] = new ToolItem (toolBar, SWT.PUSH);
				item[i].setImage(image[i][0]);
			}
			toolBar.pack ();
			shell.open ();
			
			startAnimationThreads();
			
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch ()) display.sleep ();
			}
			
			for (int i = 0; i < numToolBarItems; i++) {
				for (int j = 0; j < image[i].length; j++) {
					image[i][j].dispose();
				}
			}
			display.dispose ();
		}
	}

	private static void loadAllImages(String directory, String[] filenames) throws SWTException {
		int numItems = filenames.length;
		loader = new ImageLoader[numItems];
		imageDataArray = new ImageData[numItems][];
		image = new Image[numItems][];
		for (int i = 0; i < numItems; i++) {
			loader[i] = new ImageLoader();
			int fullWidth = loader[i].logicalScreenWidth;
			int fullHeight = loader[i].logicalScreenHeight;
			imageDataArray[i] = loader[i].load(directory + File.separator + filenames[i]);
			int numFramesOfAnimation = imageDataArray[i].length;
			image[i] = new Image[numFramesOfAnimation];
			for (int j = 0; j < numFramesOfAnimation; j++) {
				if (j == 0) {
					//for the first frame of animation, just draw the first frame
					image[i][j] = new Image(display, imageDataArray[i][j]);
					fullWidth = imageDataArray[i][j].width;
					fullHeight = imageDataArray[i][j].height;
				}
				else {
					//after the first frame of animation, draw the background or previous frame first, then the new image data 
					image[i][j] = new Image(display, fullWidth, fullHeight);
					GC gc = new GC(image[i][j]);
					gc.setBackground(shellBackground);
					gc.fillRectangle(0, 0, fullWidth, fullHeight);
					ImageData imageData = imageDataArray[i][j];
					switch (imageData.disposalMethod) {
					case SWT.DM_FILL_BACKGROUND:
						/* Fill with the background color before drawing. */
						Color bgColor = null;
						if (useGIFBackground && loader[i].backgroundPixel != -1) {
							bgColor = new Color(display, imageData.palette.getRGB(loader[i].backgroundPixel));
						}
						gc.setBackground(bgColor != null ? bgColor : shellBackground);
						gc.fillRectangle(imageData.x, imageData.y, imageData.width, imageData.height);
						if (bgColor != null) bgColor.dispose();
						break;
					default:
						/* Restore the previous image before drawing. */
						gc.drawImage(
							image[i][j-1],
							0,
							0,
							fullWidth,
							fullHeight,
							0,
							0,
							fullWidth,
							fullHeight);
						break;
					}
					Image newFrame = new Image(display, imageData);
					gc.drawImage(newFrame,
							0,
							0,
							imageData.width,
							imageData.height,
							imageData.x,
							imageData.y,
							imageData.width,
							imageData.height);
					newFrame.dispose();
					gc.dispose();
				}
			}
		}
	}

	private static void startAnimationThreads() {
		animateThread = new Thread[image.length];
		for (int ii = 0; ii < image.length; ii++) {
			final int i = ii;
			animateThread[i] = new Thread("Animation "+i) {
				int imageDataIndex = 0;
				public void run() {
					try {
						int repeatCount = loader[i].repeatCount;
						while (loader[i].repeatCount == 0 || repeatCount > 0) {
							imageDataIndex = (imageDataIndex + 1) % imageDataArray[i].length;
							if (!display.isDisposed()) {
								display.asyncExec(new Runnable() {
									public void run() {
										if (!item[i].isDisposed())
											item[i].setImage(image[i][imageDataIndex]);
									}
								});
							}
							
							/* Sleep for the specified delay time (adding commonly-used slow-down fudge factors). */
							try {
								int ms = imageDataArray[i][imageDataIndex].delayTime * 10;
								if (ms < 20) ms += 30;
								if (ms < 30) ms += 10;
								Thread.sleep(ms);
							} catch (InterruptedException e) {
							}

							/* If we have just drawn the last image, decrement the repeat count and start again. */
							if (imageDataIndex == imageDataArray[i].length - 1) repeatCount--;
						}
					} catch (SWTException ex) {
						System.out.println("There was an error animating the GIF");
						ex.printStackTrace();
					}
				}
			};
			animateThread[i].setDaemon(true);
			animateThread[i].start();
		}
	}
}
', now(), now());
insert into SNIPPET values (10208, 1, '[SWT]SnippetSnippet289.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Combo example snippet: add an item to a combo box
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet289  {
public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	final Combo combo = new Combo(shell, SWT.NONE);
	combo.setItems(new String [] {"1111", "2222", "3333", "4444"});
	combo.setText(combo.getItem(0));
	combo.addVerifyListener(new VerifyListener() {
		public void verifyText(VerifyEvent e) {
			String newText = e.text;
			try {
				Integer.parseInt(newText);
			} catch (NumberFormatException ex) {
				e.doit = false;
			}
		}
	});
	combo.addTraverseListener(new TraverseListener() {
		public void keyTraversed(TraverseEvent e) {
			if (e.detail == SWT.TRAVERSE_RETURN) {
				e.doit = false;
				e.detail = SWT.TRAVERSE_NONE;
				String newText = combo.getText();
				try {
					Integer.parseInt(newText);
					combo.add(newText);
					combo.setSelection(new Point(0, newText.length()));
				} catch (NumberFormatException ex) {
				}
			}
		}
	});

	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10209, 1, '[SWT]SnippetSnippet29.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Menu example snippet: create a bar and pull down menu (accelerators, mnemonics)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet29 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Menu bar = new Menu (shell, SWT.BAR);
	shell.setMenuBar (bar);
	MenuItem fileItem = new MenuItem (bar, SWT.CASCADE);
	fileItem.setText ("&File");
	Menu submenu = new Menu (shell, SWT.DROP_DOWN);
	fileItem.setMenu (submenu);
	MenuItem item = new MenuItem (submenu, SWT.PUSH);
	item.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			System.out.println ("Select All");
		}
	});
	item.setText ("Select &All\tCtrl+A");
	item.setAccelerator (SWT.MOD1 + A);
	shell.setSize (200, 200);
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

} 
', now(), now());
insert into SNIPPET values (10210, 1, '[SWT]SnippetSnippet290.java', '/*******************************************************************************
 * Copyright (c) 2000, 2006 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Canvas snippet: ignore 2nd mouse up event after double-click
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;

public class Snippet290 {

public static void main(String [] args) {
	final Display display = new Display();
	final Shell shell = new Shell(display);
	shell.addMouseListener(new MouseAdapter() {
		public void mouseUp(MouseEvent e) {
			if (e.count == 1) {
				System.out.println("Mouse up");
			}
		}
		public void mouseDoubleClick(MouseEvent e) {
			System.out.println("Double-click");
		}
	});
	shell.setBounds(10, 10, 200, 200);
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10211, 1, '[SWT]SnippetSnippet291.java', '/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Give accessible names to a tree and its tree items
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.accessibility.*;

public class Snippet291 {
	public static void main(String[] args) {
		final Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		final Tree tree = new Tree(shell, SWT.BORDER);
		for (int i = 0; i < 5; i++) {
			TreeItem treeItem = new TreeItem (tree, SWT.NULL);
			treeItem.setText ("item" + i);
			for (int j = 0; j < 3; j++) {
				TreeItem subItem = new TreeItem(treeItem, SWT.NONE);
				subItem.setText("item" + i + j);
			}
		}
		tree.getAccessible().addAccessibleListener(new AccessibleAdapter() {
			public void getName(AccessibleEvent e) {
				if (e.childID == ACC.CHILDID_SELF) {
					e.result = "This is the Accessible Name for the Tree";
				} else {
					TreeItem item = (TreeItem)display.findWidget(tree, e.childID);
					if (item != null) {
						e.result = "This is the Accessible Name for the TreeItem: " + item.getText();
					}
				}
			}
		});
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
}', now(), now());
insert into SNIPPET values (10212, 1, '[SWT]SnippetSnippet292.java', '/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/* 
 * Take a snapshot of a control
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet292 {
	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		final Tree tree = new Tree(shell, SWT.BORDER);
		for (int i = 0; i < 5; i++) {
			TreeItem treeItem = new TreeItem (tree, SWT.NULL);
			treeItem.setText ("TreeItem " + i);
			for (int j = 0; j < 3; j++) {
				TreeItem subItem = new TreeItem(treeItem, SWT.NONE);
				subItem.setText("SubItem " + i + "-" + j);
			}
			if (i % 3 == 0) treeItem.setExpanded (true);
		}
		final Label label = new Label (shell, SWT.NONE);
		label.addListener (SWT.Dispose, new Listener () {
			public void handleEvent (Event e) {
				Image image = label.getImage ();
				if (image != null) image.dispose ();
			}
		});
		Button button = new Button (shell, SWT.PUSH);
		button.setText ("Snapshot");
		button.addListener (SWT.Selection, new Listener () {
			public void handleEvent (Event e) {
				Image image = label.getImage ();
				if (image != null) image.dispose ();
				image = new Image (display, tree.getBounds ());
				GC gc = new GC (image);
				boolean success = tree.print (gc);
				gc.dispose ();
				label.setImage (image);
				if (!success) {
					MessageBox messageBox = new MessageBox (shell, SWT.OK | SWT.PRIMARY_MODAL);
					messageBox.setMessage ("Sorry, taking a snapshot is not supported on your platform");
					messageBox.open ();
				}
			}
		});
		GridLayout layout = new GridLayout (2, true);
		shell.setLayout(layout);
		tree.setLayoutData (new GridData (SWT.FILL, SWT.FILL, true, true));
		label.setLayoutData (new GridData (SWT.FILL, SWT.FILL, true, true));
		button.setLayoutData (new GridData (SWT.FILL, SWT.FILL, true, true, 2, 1));
		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
}', now(), now());
insert into SNIPPET values (10213, 1, '[SWT]SnippetSnippet293.java', '/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * create a tri-state button.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet293 {

	public static void main(java.lang.String[] args) {
			Display display = new Display();
			Shell shell = new Shell(display);
			shell.setLayout(new GridLayout());
			
			Button b1 = new Button (shell, SWT.CHECK);
			b1.setText("State 1");
			b1.setSelection(true);
			
			Button b2 = new Button (shell, SWT.CHECK);
			b2.setText("State 2");
			b2.setSelection(false);
			
			Button b3 = new Button (shell, SWT.CHECK);
			b3.setText("State 3");
			b3.setSelection(true);
			b3.setGrayed(true);
			
			shell.pack();
			shell.open();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
			display.dispose();
		}
}
', now(), now());
insert into SNIPPET values (10214, 1, '[SWT]SnippetSnippet294.java', '/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

/*
 * Region on a control: create a non-rectangular button
 * 
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.4
 */

public class Snippet294 {

	static int[] circle(int r, int offsetX, int offsetY) {
		int[] polygon = new int[8 * r + 4];
		// x^2 + y^2 = r^2
		for (int i = 0; i < 2 * r + 1; i++) {
			int x = i - r;
			int y = (int)Math.sqrt(r*r - x*x);
			polygon[2*i] = offsetX + x;
			polygon[2*i+1] = offsetY + y;
			polygon[8*r - 2*i - 2] = offsetX + x;
			polygon[8*r - 2*i - 1] = offsetY - y;
		}
		return polygon;
	}

	public static void main(String[] args) {
		final Display display = new Display();
		
		final Shell shell = new Shell(display);
		shell.setText("Regions on a Control");
		shell.setLayout(new FillLayout());
		shell.setBackground(display.getSystemColor(SWT.COLOR_DARK_RED));
		
		Button b2 = new Button(shell, SWT.PUSH);
		b2.setText("Button with Regions");
		
		// define a region that looks like a circle with two holes in ot
		Region region = new Region();
		region.add(circle(67, 87, 77));
		region.subtract(circle(20, 87, 47));
		region.subtract(circle(20, 87, 113));
		
		// define the shape of the button using setRegion
		b2.setRegion(region);
		b2.setLocation(100,50);
		
		b2.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				shell.close();
			}
		});
		
		shell.setSize(200,200);
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		region.dispose();
		display.dispose();
	}
	
}
', now(), now());
insert into SNIPPET values (10215, 1, '[SWT]SnippetSnippet295.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * create a dialog Shell and prompt for a text string
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet295 {

public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setText("Shell");
	FillLayout fillLayout = new FillLayout();
	fillLayout.marginWidth = 10;
	fillLayout.marginHeight = 10;
	shell.setLayout(fillLayout);

	Button open = new Button (shell, SWT.PUSH);
	open.setText ("Prompt for a String");
	open.addSelectionListener (new SelectionAdapter () {
		public void widgetSelected (SelectionEvent e) {
			final Shell dialog = new Shell (shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
			dialog.setText("Dialog Shell");
			FormLayout formLayout = new FormLayout ();
			formLayout.marginWidth = 10;
			formLayout.marginHeight = 10;
			formLayout.spacing = 10;
			dialog.setLayout (formLayout);

			Label label = new Label (dialog, SWT.NONE);
			label.setText ("Type a String:");
			FormData data = new FormData ();
			label.setLayoutData (data);

			Button cancel = new Button (dialog, SWT.PUSH);
			cancel.setText ("Cancel");
			data = new FormData ();
			data.width = 60;
			data.right = new FormAttachment (100, 0);
			data.bottom = new FormAttachment (100, 0);
			cancel.setLayoutData (data);
			cancel.addSelectionListener (new SelectionAdapter () {
				public void widgetSelected (SelectionEvent e) {
					System.out.println("User cancelled dialog");
					dialog.close ();
				}
			});

			final Text text = new Text (dialog, SWT.BORDER);
			data = new FormData ();
			data.width = 200;
			data.left = new FormAttachment (label, 0, SWT.DEFAULT);
			data.right = new FormAttachment (100, 0);
			data.top = new FormAttachment (label, 0, SWT.CENTER);
			data.bottom = new FormAttachment (cancel, 0, SWT.DEFAULT);
			text.setLayoutData (data);

			Button ok = new Button (dialog, SWT.PUSH);
			ok.setText ("OK");
			data = new FormData ();
			data.width = 60;
			data.right = new FormAttachment (cancel, 0, SWT.DEFAULT);
			data.bottom = new FormAttachment (100, 0);
			ok.setLayoutData (data);
			ok.addSelectionListener (new SelectionAdapter () {
				public void widgetSelected (SelectionEvent e) {
					System.out.println ("User typed: " + text.getText ());
					dialog.close ();
				}
			});

			dialog.setDefaultButton (ok);
			dialog.pack ();
			dialog.open ();
		}
	});
	shell.pack ();
	shell.open ();
	
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10216, 1, '[SWT]SnippetSnippet296.java', '/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * ScrolledComposite snippet: use a ScrolledComposite to scroll a Tree vertically
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet296 {
	
public static void main (String[] args) {
	final Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setBounds (10, 10, 300, 300);
	final ScrolledComposite sc = new ScrolledComposite (shell, SWT.VERTICAL);
	sc.setBounds (10, 10, 280, 200);
	final int clientWidth = sc.getClientArea ().width;

	final Tree tree = new Tree (sc, SWT.NONE);
	for (int i = 0; i < 99; i++) {
		TreeItem item = new TreeItem (tree, SWT.NONE);
		item.setText ("item " + i);
		new TreeItem (item, SWT.NONE).setText ("child");
	}
	sc.setContent (tree);
	int prefHeight = tree.computeSize (SWT.DEFAULT, SWT.DEFAULT).y;
	tree.setSize (clientWidth, prefHeight);
	/*
	 * The following listener ensures that the Tree is always large
	 * enough to not need to show its own vertical scrollbar.
	 */
	tree.addTreeListener (new TreeListener () {
		public void treeExpanded (TreeEvent e) {
			int prefHeight = tree.computeSize (SWT.DEFAULT, SWT.DEFAULT).y;
			tree.setSize (clientWidth, prefHeight);
		}
		public void treeCollapsed (TreeEvent e) {
			int prefHeight = tree.computeSize (SWT.DEFAULT, SWT.DEFAULT).y;
			tree.setSize (clientWidth, prefHeight);
		}
	});
	/*
	 * The following listener ensures that a newly-selected item
	 * in the Tree is always visible.
	 */
	tree.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			TreeItem [] selectedItems = tree.getSelection();
			if (selectedItems.length > 0) {
				Rectangle itemRect = selectedItems[0].getBounds();
				Rectangle area = sc.getClientArea();
				Point origin = sc.getOrigin();
				if (itemRect.x < origin.x || itemRect.y < origin.y
						|| itemRect.x + itemRect.width > origin.x + area.width
						|| itemRect.y + itemRect.height > origin.y + area.height) {
					sc.setOrigin(itemRect.x, itemRect.y);
				}
			}
		}
	});

	Button downButton = new Button (shell, SWT.PUSH);
	downButton.setBounds (10, 220, 120, 30);
	downButton.setText ("Down 10px");
	downButton.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event event) {
			sc.setOrigin (0, sc.getOrigin ().y + 10);
		}
	});
	Button upButton = new Button (shell, SWT.PUSH);
	upButton.setBounds (140, 220, 120, 30);
	upButton.setText ("Up 10px");
	upButton.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event event) {
			sc.setOrigin (0, sc.getOrigin ().y - 10);
		}
	});
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10217, 1, '[SWT]SnippetSnippet297.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table example snippet: create a table with column header images
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet297 {

public static void main (String [] args) {
	Display display = new Display ();
	Image images[] = new Image[] {
		display.getSystemImage(SWT.ICON_INFORMATION),
		display.getSystemImage(SWT.ICON_ERROR),
		display.getSystemImage(SWT.ICON_QUESTION),
		display.getSystemImage(SWT.ICON_WARNING),
	};
	String[] titles = {"Information", "Error", "Question", "Warning"};
	String[] questions = {"who?", "what?", "where?", "when?", "why?"};
	Shell shell = new Shell (display);
	shell.setLayout(new GridLayout());
	Table table = new Table (shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
	GridData data = new GridData (SWT.FILL, SWT.FILL, true, true);
	data.heightHint = 200;
	table.setLayoutData (data);
	table.setLinesVisible (true);
	table.setHeaderVisible (true);
	for (int i=0; i<titles.length; i++) {
		TableColumn column = new TableColumn (table, SWT.NONE);
		column.setText (titles [i]);
		column.setImage(images [i]);
	}	
	int count = 128;
	for (int i=0; i<count; i++) {
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText (0, "some info");
		item.setText (1, "error #" + i);
		item.setText (2, questions [i % questions.length]);
		item.setText (3, "look out!");
	}
	for (int i=0; i<titles.length; i++) {
		table.getColumn (i).pack ();
	}	
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10218, 1, '[SWT]SnippetSnippet298.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Transform snippet: shear images
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet298 {

public static void main (String [] args) {
	final Display display = new Display();
	final Shell shell = new Shell(display);
	shell.addListener(SWT.Paint, new Listener() {
		public void handleEvent(Event event) {
			int[] icons = new int[]{SWT.ICON_ERROR, SWT.ICON_WARNING, SWT.ICON_INFORMATION, SWT.ICON_QUESTION, SWT.ICON_WORKING};
			int x = 10;
			for (int i = 0; i < icons.length; i++) {
				Image image = display.getSystemImage(icons[i]);
				if (image != null) {
					Transform t = new Transform(display);
					t.translate(x, 10);
					t.shear(1, 0);
					GC gc = event.gc;
					gc.setTransform(t);
					t.dispose();
					gc.drawImage(image, 0, 0);
					x += image.getBounds().width + 10;
				}
			}
		}
	});
	shell.setSize(260, 100);
	shell.open();	
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
} 
', now(), now());
insert into SNIPPET values (10219, 1, '[SWT]SnippetSnippet299.java', 'package org.eclipse.swt.snippets;

/*
 * RowLayout: center alignment
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */

import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet299 {

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		RowLayout layout = new RowLayout();
		layout.center = true;
		shell.setLayout(layout);

		Button button0 = new Button(shell, SWT.PUSH);
		button0.setText("Button 0");
		
		Button button1 = new Button(shell, SWT.PUSH);
		button1.setText("Button 1");
		button1.setLayoutData(new RowData (SWT.DEFAULT, 50));
		
		Button button2 = new Button(shell, SWT.PUSH);
		button2.setText("Button 2");
		button2.setLayoutData(new RowData (SWT.DEFAULT, 70));

		Button button3 = new Button(shell, SWT.PUSH);
		button3.setText("Button 3");

		shell.pack();
		shell.open();
		
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}
} 
', now(), now());
insert into SNIPPET values (10220, 1, '[SWT]SnippetSnippet3.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table example snippet: find a table cell from mouse down (SWT.FULL_SELECTION)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.graphics.*;

public class Snippet3 {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	final Table table = new Table(shell, SWT.BORDER | SWT.V_SCROLL
			| SWT.FULL_SELECTION);
	table.setHeaderVisible(true);
	table.setLinesVisible(true);
	final int rowCount = 64, columnCount = 4;
	for (int i = 0; i < columnCount; i++) {
		TableColumn column = new TableColumn(table, SWT.NONE);
		column.setText("Column " + i);
	}
	for (int i = 0; i < rowCount; i++) {
		TableItem item = new TableItem(table, SWT.NONE);
		for (int j = 0; j < columnCount; j++) {
			item.setText(j, "Item " + i + "-" + j);
		}
	}
	for (int i = 0; i < columnCount; i++) {
		table.getColumn(i).pack();
	}
	Point size = table.computeSize(SWT.DEFAULT, 200);
	table.setSize(size);
	shell.pack();
	table.addListener(SWT.MouseDown, new Listener() {
		public void handleEvent(Event event) {
			Point pt = new Point(event.x, event.y);
			TableItem item = table.getItem(pt);
			if (item == null)
				return;
			for (int i = 0; i < columnCount; i++) {
				Rectangle rect = item.getBounds(i);
				if (rect.contains(pt)) {
					int index = table.indexOf(item);
					System.out.println("Item " + index + "-" + i);
				}
			}
		}
	});
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}

}
', now(), now());
insert into SNIPPET values (10221, 1, '[SWT]SnippetSnippet30.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Program example snippet: invoke the system text editor on autoexec.bat
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.program.*;
import org.eclipse.swt.widgets.*;

public class Snippet30 {

public static void main (String [] args) {
	Display display = new Display ();
	Program p = Program.findProgram (".txt");
	if (p != null) p.execute ("c:\\autoexec.bat"); // Windows-specific filename
	display.dispose ();
}

} 
', now(), now());
insert into SNIPPET values (10222, 1, '[SWT]SnippetSnippet300.java', '/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * example snippet: DND between Swing and SWT
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import javax.swing.*;

import org.eclipse.swt.*;
import org.eclipse.swt.awt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet300 {

	public static void main(String[] args) {
		final Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setText("SWT and Swing DND Example");
		GridLayout layout = new GridLayout(1, false);
		shell.setLayout(layout);

		Text swtText = new Text(shell, SWT.BORDER);
		swtText.setText("SWT Text");
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		swtText.setLayoutData(data);
		setDragDrop(swtText);

		Composite comp = new Composite(shell, SWT.EMBEDDED);
		java.awt.Frame frame = SWT_AWT.new_Frame(comp);
		JTextField swingText = new JTextField(40);
		swingText.setText("Swing Text");
		swingText.setDragEnabled(true);
		frame.add(swingText);
		data = new GridData(GridData.FILL_HORIZONTAL);
		data.heightHint = swingText.getPreferredSize().height;
		comp.setLayoutData(data);

		shell.setSize(400, 200);
		shell.open();
		while(!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
		display.dispose();
	}
	public static void setDragDrop (final Text text) {
		Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
		int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
		
		final DragSource source = new DragSource (text, operations);
		source.setTransfer(types);
		source.addDragListener (new DragSourceListener () {
			Point selection;
			public void dragStart(DragSourceEvent e) {
				selection = text.getSelection();
				e.doit = selection.x != selection.y;
			}
			public void dragSetData(DragSourceEvent e) {
				e.data = text.getText(selection.x, selection.y-1);
			}
			public void dragFinished(DragSourceEvent e) {
				if (e.detail == DND.DROP_MOVE) {
					text.setSelection(selection);
					text.insert("");
				}
				selection = null;
			}
		});

		DropTarget target = new DropTarget(text, operations);
		target.setTransfer(types);
		target.addDropListener (new DropTargetAdapter() {
			public void drop(DropTargetEvent event) {
				if (event.data == null) {
					event.detail = DND.DROP_NONE;
					return;
				}
				text.append((String) event.data);
			}
		});
	}
}
', now(), now());
insert into SNIPPET values (10223, 1, '[SWT]SnippetSnippet301.java', '/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table example snippet: create a table with no scroll bars
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet301 {
	public static void main (String [] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
		FillLayout layout = new FillLayout();
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		shell.setLayout(layout);
		Table table = new Table (shell, SWT.BORDER | SWT.NO_SCROLL);
		for (int i=0; i<10; i++) {
			TableItem item = new TableItem (table, SWT.NONE);
			item.setText ("Item " + i);
		}
		shell.pack ();
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}


}
', now(), now());
insert into SNIPPET values (10224, 1, '[SWT]SnippetSnippet302.java', '/*******************************************************************************
 * Copyright (c) 2000, 2008 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tree example snippet: create a tree with no scroll bars
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet302 {
	public static void main (String [] args) {
		Display display = new Display ();
		Shell shell = new Shell (display);
		FillLayout layout = new FillLayout();
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		shell.setLayout(layout);
		Tree tree = new Tree (shell, SWT.BORDER | SWT.NO_SCROLL);
		for (int i=0; i<10; i++) {
			TreeItem item = new TreeItem (tree, SWT.NONE);
			item.setText ("Item " + i);
		}
		shell.pack ();
		shell.open ();
		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose ();
	}


}
', now(), now());
insert into SNIPPET values (10225, 1, '[SWT]SnippetSnippet31.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tracker example snippet: create a tracker (drag when "torn off")
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet31 {

public static void main (String [] args) {
	final Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setSize (200, 200);
	shell.open ();
	Listener listener = new Listener () {
		Point point = null;
		static final int JITTER = 8;
		public void handleEvent (Event event) {
			switch (event.type) {
				case SWT.MouseDown:
					point = new Point (event.x, event.y);
					break;
				case SWT.MouseMove:
					if (point == null) return;
					int deltaX = point.x - event.x, deltaY = point.y - event.y;
					if (Math.abs (deltaX) < JITTER && Math.abs (deltaY) < JITTER) {
						return;
					}
					Tracker tracker = new Tracker (display, SWT.NONE);
					Rectangle rect = display.map (shell, null, shell.getClientArea ());
					rect.x -= deltaX;
					rect.y -= deltaY;
					tracker.setRectangles (new Rectangle [] {rect});
					tracker.open ();
					//FALL THROUGH
				case SWT.MouseUp:
					point = null;
					break;
			}
		}
	};
	shell.addListener (SWT.MouseDown, listener);
	shell.addListener (SWT.MouseMove, listener);
	shell.addListener (SWT.MouseUp, listener);
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10226, 1, '[SWT]SnippetSnippet32.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Program example snippet: find the icon of the program that edits .bmp files
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.program.*;

public class Snippet32 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Label label = new Label (shell, SWT.NONE);
	label.setText ("Cant find icon for .bmp");
	Image image = null;
	Program p = Program.findProgram (".bmp");
	if (p != null) {
		ImageData data = p.getImageData ();
		if (data != null) {
			image = new Image (display, data);
			label.setImage (image);
		}
	}
	label.pack ();
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	if (image != null) image.dispose ();
	display.dispose ();
}

} 
', now(), now());
insert into SNIPPET values (10227, 1, '[SWT]SnippetSnippet33.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * DirectoryDialog example snippet: prompt for a directory
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.widgets.*;

public class Snippet33 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.open ();
	DirectoryDialog dialog = new DirectoryDialog (shell);
	dialog.setFilterPath ("c:\\"); //Windows specific
	System.out.println ("RESULT=" + dialog.open ());
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

} 
', now(), now());
insert into SNIPPET values (10228, 1, '[SWT]SnippetSnippet34.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Label example snippet: create a label (with an image)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet34 {

public static void main (String[] args) {
	Display display = new Display();
	Image image = new Image (display, 16, 16);
	Color color = display.getSystemColor (SWT.COLOR_RED);
	GC gc = new GC (image);
	gc.setBackground (color);
	gc.fillRectangle (image.getBounds ());
	gc.dispose ();
	Shell shell = new Shell (display);
	Label label = new Label (shell, SWT.BORDER);
	label.setImage (image);
	label.pack ();
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep (); 
	} 
	image.dispose ();
	display.dispose ();
}

} 
', now(), now());
insert into SNIPPET values (10229, 1, '[SWT]SnippetSnippet35.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Table example snippet: create a table (no columns, no headers)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet35 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Table table = new Table (shell, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
	for (int i=0; i<12; i++) {
		TableItem item = new TableItem (table, 0);
		item.setText ("Item " + i);
	}
	table.setSize (100, 100);
	shell.setSize (200, 200);
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

} 
', now(), now());
insert into SNIPPET values (10230, 1, '[SWT]SnippetSnippet36.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * ToolBar example snippet: create a flat tool bar (images)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet36 {

public static void main (String [] args) {
	Display display = new Display();
	Image image = new Image (display, 16, 16);
	Color color = display.getSystemColor (SWT.COLOR_RED);
	GC gc = new GC (image);
	gc.setBackground (color);
	gc.fillRectangle (image.getBounds ());
	gc.dispose ();
	Shell shell = new Shell (display);
	ToolBar toolBar = new ToolBar (shell, SWT.FLAT | SWT.BORDER);
	for (int i=0; i<12; i++) {
		ToolItem item = new ToolItem (toolBar, SWT.DROP_DOWN);
		item.setImage (image);
	}
	toolBar.pack ();
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	image.dispose ();
	display.dispose ();
}

} 
', now(), now());
insert into SNIPPET values (10231, 1, '[SWT]SnippetSnippet37.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Label example snippet: create a label (a separator)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet37 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	new Label (shell, SWT.SEPARATOR | SWT.HORIZONTAL);
	new Label (shell, SWT.SEPARATOR | SWT.VERTICAL);
	shell.setSize (200, 200);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10232, 1, '[SWT]SnippetSnippet38.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table example snippet: create a table (columns, headers, lines)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet38 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new GridLayout());
	Table table = new Table (shell, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
	table.setLinesVisible (true);
	table.setHeaderVisible (true);
	GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
	data.heightHint = 200;
	table.setLayoutData(data);
	String[] titles = {" ", "C", "!", "Description", "Resource", "In Folder", "Location"};
	for (int i=0; i<titles.length; i++) {
		TableColumn column = new TableColumn (table, SWT.NONE);
		column.setText (titles [i]);
	}	
	int count = 128;
	for (int i=0; i<count; i++) {
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText (0, "x");
		item.setText (1, "y");
		item.setText (2, "!");
		item.setText (3, "this stuff behaves the way I expect");
		item.setText (4, "almost everywhere");
		item.setText (5, "some.folder");
		item.setText (6, "line " + i + " in nowhere");
	}
	for (int i=0; i<titles.length; i++) {
		table.getColumn (i).pack ();
	}	
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10233, 1, '[SWT]SnippetSnippet39.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * CCombo example snippet: create a CCombo
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet39 {
	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout());
		
		CCombo combo = new CCombo(shell, SWT.FLAT | SWT.BORDER);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
		for (int i = 0; i < 5; i++) {
			combo.add("item" + i);
		}
		combo.setText("item0");

		combo.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.out.println("Item selected");
			};
		});

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) display.sleep();
		}
	}
}
', now(), now());
insert into SNIPPET values (10234, 1, '[SWT]SnippetSnippet4.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Shell example snippet: prevent escape from closing a dialog
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.widgets.*;

public class Snippet4 {

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		Button b = new Button(shell, SWT.PUSH);
		b.setText("Open Dialog ...");
		b.pack();
		b.setLocation(10, 10);
		b.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent se) {
				Shell dialog = new Shell(shell, SWT.DIALOG_TRIM);
				dialog.addListener(SWT.Traverse, new Listener() {
					public void handleEvent(Event e) {
						if (e.detail == SWT.TRAVERSE_ESCAPE) {
							e.doit = false;
						}
					}
				});
				dialog.open();
			}
		});
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
	}

}
', now(), now());
insert into SNIPPET values (10235, 1, '[SWT]SnippetSnippet40.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Menu example snippet: create a popup menu (set in multiple controls)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet40 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Composite c1 = new Composite (shell, SWT.BORDER);
	c1.setSize (100, 100);
	Composite c2 = new Composite (shell, SWT.BORDER);
	c2.setBounds (100, 0, 100, 100);
	Menu menu = new Menu (shell, SWT.POP_UP);
	MenuItem item = new MenuItem (menu, SWT.PUSH);
	item.setText ("Popup");
	c1.setMenu (menu);
	c2.setMenu (menu);
	shell.setMenu (menu);
	shell.setSize (300, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10236, 1, '[SWT]SnippetSnippet41.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tool Tips example snippet: create tool tips for a tab item, tool item, and shell
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet41 {

public static void main (String [] args) {
	String string = "This is a string\nwith a new line.";
	Display display = new Display ();
	Shell shell = new Shell (display);
	TabFolder folder = new TabFolder (shell, SWT.BORDER);
	folder.setSize (200, 200);
	TabItem item0 = new TabItem (folder, 0);
	item0.setToolTipText ("TabItem toolTip: " + string);
	ToolBar bar = new ToolBar (shell, SWT.BORDER);
	bar.setBounds (0, 200, 200, 64);
	ToolItem item1 = new ToolItem (bar, 0);
	item1.setToolTipText ("ToolItem toolTip: " + string);
	shell.setToolTipText ("Shell toolTip: " + string);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10237, 1, '[SWT]SnippetSnippet42.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Display example snippet: get the bounds and client area of a display
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.widgets.*;

public class Snippet42 {

public static void main (String [] args) {
	Display display = new Display ();
	System.out.println ("Display Bounds=" + display.getBounds () + " Display ClientArea=" + display.getClientArea ());
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10238, 1, '[SWT]SnippetSnippet43.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Caret example snippet: create a caret (using an image)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet43 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.open ();
	Caret caret = new Caret (shell, SWT.NONE);
	Color white = display.getSystemColor (SWT.COLOR_WHITE);
	Color black = display.getSystemColor (SWT.COLOR_BLACK);
	Image image = new Image (display, 20, 20);
	GC gc = new GC (image);
	gc.setBackground (black);
	gc.fillRectangle (0, 0, 20, 20);
	gc.setForeground (white);
	gc.drawLine (0, 0, 19, 19);
	gc.drawLine (19, 0, 0, 19);
	gc.dispose ();
	caret.setLocation (10, 10);
	caret.setImage (image);
	gc = new GC (shell);
	gc.drawImage (image, 10, 64);
	caret.setVisible (false);
	gc.drawString ("Test", 12, 12);
	caret.setVisible (true);
	gc.dispose ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	image.dispose ();
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10239, 1, '[SWT]SnippetSnippet44.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Cursor example snippet: set the hand cursor into a control
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet44 {

public static void main (String [] args) {
	Display display = new Display ();
	final Cursor cursor = new Cursor (display, SWT.CURSOR_HAND);
	Shell shell = new Shell (display);
	shell.open ();
	final Button b = new Button (shell, 0);
	b.setBounds (10, 10, 200, 200);
	b.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			b.setCursor (cursor);
		}
	});
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	cursor.dispose ();
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10240, 1, '[SWT]SnippetSnippet45.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Scale example snippet: create a scale (maximum 40, page increment 5)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet45 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Scale scale = new Scale (shell, SWT.BORDER);
	scale.setSize (200, 64);
	scale.setMaximum (40);
	scale.setPageIncrement (5);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10241, 1, '[SWT]SnippetSnippet46.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Composite example snippet: intercept mouse events (drag a button with the mouse)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet46 {
public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	final Composite composite = new Composite (shell, SWT.NONE);
	composite.setEnabled (false);
	composite.setLayout (new FillLayout ());
	Button button = new Button (composite, SWT.PUSH);
	button.setText ("Button");
	composite.pack ();
	composite.setLocation (10, 10);
	final Point [] offset = new Point [1];
	Listener listener = new Listener () {
		public void handleEvent (Event event) {
			switch (event.type) {
				case SWT.MouseDown:
					Rectangle rect = composite.getBounds ();
					if (rect.contains (event.x, event.y)) {
						Point pt1 = composite.toDisplay (0, 0);
						Point pt2 = shell.toDisplay (event.x, event.y); 
						offset [0] = new Point (pt2.x - pt1.x, pt2.y - pt1.y);
					}
					break;
				case SWT.MouseMove:
					if (offset [0] != null) {
						Point pt = offset [0];
						composite.setLocation (event.x - pt.x, event.y - pt.y);
					}
					break;
				case SWT.MouseUp:
					offset [0] = null;
					break;
			}
		}
	};
	shell.addListener (SWT.MouseDown, listener);
	shell.addListener (SWT.MouseUp, listener);
	shell.addListener (SWT.MouseMove, listener);
	shell.setSize (300, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10242, 1, '[SWT]SnippetSnippet47.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * ToolBar example snippet: create tool bar (normal, hot and disabled images)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet47 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);

	Image image = new Image (display, 20, 20);
	Color color = display.getSystemColor (SWT.COLOR_BLUE);
	GC gc = new GC (image);
	gc.setBackground (color);
	gc.fillRectangle (image.getBounds ());
	gc.dispose ();
	
	Image disabledImage = new Image (display, 20, 20);
	color = display.getSystemColor (SWT.COLOR_GREEN);
	gc = new GC (disabledImage);
	gc.setBackground (color);
	gc.fillRectangle (disabledImage.getBounds ());
	gc.dispose ();
	
	Image hotImage = new Image (display, 20, 20);
	color = display.getSystemColor (SWT.COLOR_RED);
	gc = new GC (hotImage);
	gc.setBackground (color);
	gc.fillRectangle (hotImage.getBounds ());
	gc.dispose ();
	
	ToolBar bar = new ToolBar (shell, SWT.BORDER | SWT.FLAT);
	bar.setSize (200, 32);
	for (int i=0; i<12; i++) {
		ToolItem item = new ToolItem (bar, 0);
		item.setImage (image);
		item.setDisabledImage (disabledImage);
		item.setHotImage (hotImage);
		if (i % 3 == 0) item.setEnabled (false);
	}
	
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	image.dispose ();
	disabledImage.dispose ();
	hotImage.dispose ();
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10243, 1, '[SWT]SnippetSnippet48.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Canvas example snippet: scroll an image (flicker free, no double buffering)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet48 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	Image originalImage = null;
	FileDialog dialog = new FileDialog (shell, SWT.OPEN);
	dialog.setText ("Open an image file or cancel");
	String string = dialog.open ();
	if (string != null) {
		originalImage = new Image (display, string);
	}
	if (originalImage == null) {
		int width = 150, height = 200;
		originalImage = new Image (display, width, height);
		GC gc = new GC (originalImage);
		gc.fillRectangle (0, 0, width, height);
		gc.drawLine (0, 0, width, height);
		gc.drawLine (0, height, width, 0);
		gc.drawText ("Default Image", 10, 10);
		gc.dispose ();
	}
	final Image image = originalImage;
	final Point origin = new Point (0, 0);
	final Canvas canvas = new Canvas (shell, SWT.NO_BACKGROUND |
			SWT.NO_REDRAW_RESIZE | SWT.V_SCROLL | SWT.H_SCROLL);
	final ScrollBar hBar = canvas.getHorizontalBar ();
	hBar.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			int hSelection = hBar.getSelection ();
			int destX = -hSelection - origin.x;
			Rectangle rect = image.getBounds ();
			canvas.scroll (destX, 0, 0, 0, rect.width, rect.height, false);
			origin.x = -hSelection;
		}
	});
	final ScrollBar vBar = canvas.getVerticalBar ();
	vBar.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			int vSelection = vBar.getSelection ();
			int destY = -vSelection - origin.y;
			Rectangle rect = image.getBounds ();
			canvas.scroll (0, destY, 0, 0, rect.width, rect.height, false);
			origin.y = -vSelection;
		}
	});
	canvas.addListener (SWT.Resize,  new Listener () {
		public void handleEvent (Event e) {
			Rectangle rect = image.getBounds ();
			Rectangle client = canvas.getClientArea ();
			hBar.setMaximum (rect.width);
			vBar.setMaximum (rect.height);
			hBar.setThumb (Math.min (rect.width, client.width));
			vBar.setThumb (Math.min (rect.height, client.height));
			int hPage = rect.width - client.width;
			int vPage = rect.height - client.height;
			int hSelection = hBar.getSelection ();
			int vSelection = vBar.getSelection ();
			if (hSelection >= hPage) {
				if (hPage <= 0) hSelection = 0;
				origin.x = -hSelection;
			}
			if (vSelection >= vPage) {
				if (vPage <= 0) vSelection = 0;
				origin.y = -vSelection;
			}
			canvas.redraw ();
		}
	});
	canvas.addListener (SWT.Paint, new Listener () {
		public void handleEvent (Event e) {
			GC gc = e.gc;
			gc.drawImage (image, origin.x, origin.y);
			Rectangle rect = image.getBounds ();
			Rectangle client = canvas.getClientArea ();
			int marginWidth = client.width - rect.width;
			if (marginWidth > 0) {
				gc.fillRectangle (rect.width, 0, marginWidth, client.height);
			}
			int marginHeight = client.height - rect.height;
			if (marginHeight > 0) {
				gc.fillRectangle (0, rect.height, client.width, marginHeight);
			}
		}
	});
	shell.setSize (200, 150);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	originalImage.dispose();
	display.dispose ();
}

} 
', now(), now());
insert into SNIPPET values (10244, 1, '[SWT]SnippetSnippet49.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * ToolBar example snippet: create tool bar (wrap on resize)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet49 {

public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	final ToolBar toolBar = new ToolBar (shell, SWT.WRAP);
	for (int i=0; i<12; i++) {
		ToolItem item = new ToolItem (toolBar, SWT.PUSH);
		item.setText ("Item " + i);
	}
	shell.addListener (SWT.Resize, new Listener () {
		public void handleEvent (Event e) {
			Rectangle rect = shell.getClientArea ();
			Point size = toolBar.computeSize (rect.width, SWT.DEFAULT);
			toolBar.setSize (size);
		}
	});
	toolBar.pack ();
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10245, 1, '[SWT]SnippetSnippet5.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * ScrolledComposite example snippet: scroll a control in a scrolled composite
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.custom.*;

public class Snippet5 {

public static void main (String [] args) 
{
    Display display = new Display ();
    Shell shell = new Shell (display);
    shell.setLayout(new FillLayout());

    // this button is always 400 x 400. Scrollbars appear if the window is resized to be
    // too small to show part of the button
    ScrolledComposite c1 = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    Button b1 = new Button(c1, SWT.PUSH);
    b1.setText("fixed size button");
    b1.setSize(400, 400);
    c1.setContent(b1);

    // this button has a minimum size of 400 x 400. If the window is resized to be big
    // enough to show more than 400 x 400, the button will grow in size. If the window
    // is made too small to show 400 x 400, scrollbars will appear.
    ScrolledComposite c2 = new ScrolledComposite(shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    Button b2 = new Button(c2, SWT.PUSH);
    b2.setText("expanding button");
    c2.setContent(b2);
    c2.setExpandHorizontal(true);
    c2.setExpandVertical(true);
    c2.setMinWidth(400);
    c2.setMinHeight(400);

    shell.setSize(600, 300);
    shell.open ();
    while (!shell.isDisposed ()) {
        if (!display.readAndDispatch ()) display.sleep ();
    }
    display.dispose ();
}

}
', now(), now());
insert into SNIPPET values (10246, 1, '[SWT]SnippetSnippet50.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Shell example snippet: create a dialog shell
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.widgets.*;

public class Snippet50 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setText ("Shell");
	shell.setSize (200, 200);
	shell.open ();
	Shell dialog = new Shell (shell);
	dialog.setText ("Dialog");
	dialog.setSize (200, 200);
	dialog.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10247, 1, '[SWT]SnippetSnippet51.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table example snippet: scroll a table (set the top index)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet51 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Table table = new Table (shell, SWT.BORDER | SWT.MULTI);
	table.setSize (200, 200);
	for (int i=0; i<128; i++) {
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText ("Item " + i);
	}
	table.setTopIndex (95);
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10248, 1, '[SWT]SnippetSnippet52.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table example snippet: select an index (select and scroll)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet52 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Table table = new Table (shell, SWT.BORDER | SWT.MULTI);
	table.setSize (200, 200);
	for (int i=0; i<128; i++) {
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText ("Item " + i);
	}
	table.setSelection (95);
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10249, 1, '[SWT]SnippetSnippet53.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Table example snippet: remove selected items
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet53 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	final Table table = new Table (shell, SWT.BORDER | SWT.MULTI);
	table.setSize (200, 200);
	for (int i=0; i<128; i++) {
		TableItem item = new TableItem (table, SWT.NONE);
		item.setText ("Item " + i);
	}
	Menu menu = new Menu (shell, SWT.POP_UP);
	table.setMenu (menu);
	MenuItem item = new MenuItem (menu, SWT.PUSH);
	item.setText ("Delete Selection");
	item.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event event) {
			table.remove (table.getSelectionIndices ());
		}
	});
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10250, 1, '[SWT]SnippetSnippet54.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Sash example snippet: create a sash (allow it to be moved)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet54 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	final Sash sash = new Sash (shell, SWT.BORDER | SWT.VERTICAL);
	sash.setBounds (10, 10, 32, 100);
	sash.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			sash.setBounds (e.x, e.y, e.width, e.height);
		}
	});
	shell.open ();
	sash.setFocus ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10251, 1, '[SWT]SnippetSnippet55.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Text example snippet: resize a text control (show about 10 characters)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet55 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Text text = new Text (shell, SWT.BORDER);
	int columns = 10;
	GC gc = new GC (text);
	FontMetrics fm = gc.getFontMetrics ();
	int width = columns * fm.getAverageCharWidth ();
	int height = fm.getHeight ();
	gc.dispose ();
	text.setSize (text.computeSize (width, height));
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10252, 1, '[SWT]SnippetSnippet56.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * ProgressBar example snippet: update a progress bar (from another thread)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet56 {

	public static void main(String[] args) {
		final Display display = new Display();
		Shell shell = new Shell(display);
		final ProgressBar bar = new ProgressBar(shell, SWT.SMOOTH);
		bar.setBounds(10, 10, 200, 32);
		shell.open();
		final int maximum = bar.getMaximum();
		new Thread() {
			public void run() {
				for (final int[] i = new int[1]; i[0] <= maximum; i[0]++) {
				try {Thread.sleep (100);} catch (Throwable th) {}
					if (display.isDisposed()) return;
					display.asyncExec(new Runnable() {
						public void run() {
						if (bar.isDisposed ()) return;
							bar.setSelection(i[0]);
						}
					});
				}
			}
		}.start();
		while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
		}
		display.dispose();
	}
}', now(), now());
insert into SNIPPET values (10253, 1, '[SWT]SnippetSnippet57.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * ProgressBar example snippet: update a progress bar (from the UI thread)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet57 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	ProgressBar bar = new ProgressBar (shell, SWT.SMOOTH);
	bar.setBounds (10, 10, 200, 32);
	shell.open ();
	for (int i=0; i<=bar.getMaximum (); i++) {
		try {Thread.sleep (100);} catch (Throwable th) {}
		bar.setSelection (i);
	}
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10254, 1, '[SWT]SnippetSnippet58.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * ToolBar example snippet: place a combo box in a tool bar
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet58 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	ToolBar bar = new ToolBar (shell, SWT.BORDER);
	for (int i=0; i<4; i++) {
		ToolItem item = new ToolItem (bar, 0);
		item.setText ("Item " + i);
	}
	ToolItem sep = new ToolItem (bar, SWT.SEPARATOR);
	int start = bar.getItemCount ();
	for (int i=start; i<start+4; i++) {
		ToolItem item = new ToolItem (bar, 0);
		item.setText ("Item " + i);
	}
	Combo combo = new Combo (bar, SWT.READ_ONLY);
	for (int i=0; i<4; i++) {
		combo.add ("Item " + i);
	}
	combo.pack ();
	sep.setWidth (combo.getSize ().x);
	sep.setControl (combo);
	bar.pack ();
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10255, 1, '[SWT]SnippetSnippet59.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * List example snippet: print selected items in a list
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet59 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	final List list = new List (shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	for (int i=0; i<128; i++) list.add ("Item " + i);
	list.setBounds (0, 0, 100, 100);
	list.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			String string = "";
			int [] selection = list.getSelectionIndices ();
			for (int i=0; i<selection.length; i++) string += selection [i] + " ";
			System.out.println ("Selection={" + string + "}");
		}
	});
	list.addListener (SWT.DefaultSelection, new Listener () {
		public void handleEvent (Event e) {
			String string = "";
			int [] selection = list.getSelectionIndices ();
			for (int i=0; i<selection.length; i++) string += selection [i] + " ";
			System.out.println ("DefaultSelection={" + string + "}");
		}
	});
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10256, 1, '[SWT]SnippetSnippet6.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * GridLayout example snippet: insert widgets into a grid layout
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet6 {

public static void main (String [] args) {
    Display display = new Display ();
    final Shell shell = new Shell (display);
    shell.setLayout(new GridLayout());
    final Composite c = new Composite(shell, SWT.NONE);
    GridLayout layout = new GridLayout();
    layout.numColumns = 3;
    c.setLayout(layout);
    for (int i = 0; i < 10; i++) {
        Button b = new Button(c, SWT.PUSH);
        b.setText("Button "+i);
    }

    Button b = new Button(shell, SWT.PUSH);
    b.setText("add a new button at row 2 column 1");
    final int[] index = new int[1];
    b.addListener(SWT.Selection, new Listener() {
        public void handleEvent(Event e) {
            Button s = new Button(c, SWT.PUSH);
            s.setText("Special "+index[0]);
            index[0]++;
            Control[] children = c.getChildren();
            s.moveAbove(children[3]);
            shell.layout(new Control[] {s});
        }
    });

    shell.open ();
    while (!shell.isDisposed ()) {
        if (!display.readAndDispatch ()) display.sleep ();
    }
    display.dispose ();
}

}
', now(), now());
insert into SNIPPET values (10257, 1, '[SWT]SnippetSnippet60.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Display example snippet: create two one shot timers (5000 ms, 2000 ms)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.widgets.*;

public class Snippet60 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setSize (200, 200);
	shell.open ();
	display.timerExec (5000, new Runnable () {
		public void run () {
			System.out.println ("5000");
		}
	});
	display.timerExec (2000, new Runnable () {
		public void run () {
			System.out.println ("2000");
		}
	});
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10258, 1, '[SWT]SnippetSnippet61.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tree example snippet: print selected items in a tree
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet61 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	final Tree tree = new Tree (shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	for (int i=0; i<4; i++) {
		TreeItem item0 = new TreeItem (tree, 0);
		item0.setText ("Item " + i);
		for (int j=0; j<4; j++) {
			TreeItem item1 = new TreeItem (item0, 0);
			item1.setText ("SubItem " + i + " " + j);
			for (int k=0; k<4; k++) {
				TreeItem item2 = new TreeItem (item1, 0);
				item2.setText ("SubItem " + i + " " + j + " " + k);
			}	
		}
	}
	tree.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			String string = "";
			TreeItem [] selection = tree.getSelection ();
			for (int i=0; i<selection.length; i++) string += selection [i] + " ";
			System.out.println ("Selection={" + string + "}");
		}
	});
	tree.addListener (SWT.DefaultSelection, new Listener () {
		public void handleEvent (Event e) {
			String string = "";
			TreeItem [] selection = tree.getSelection ();
			for (int i=0; i<selection.length; i++) string += selection [i] + " ";
			System.out.println ("DefaultSelection={" + string + "}");
		}
	});
	tree.addListener (SWT.Expand, new Listener () {
		public void handleEvent (Event e) {
			System.out.println ("Expand={" + e.item + "}");
		}
	});
	tree.addListener (SWT.Collapse, new Listener () {
		public void handleEvent (Event e) {
			System.out.println ("Collapse={" + e.item + "}");
		}
	});
	tree.getItems () [0].setExpanded (true);
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10259, 1, '[SWT]SnippetSnippet62.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Control example snippet: print mouse state and button (down, move, up)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet62 {

static String stateMask (int stateMask) {
	String string = "";
	if ((stateMask & SWT.CTRL) != 0) string += " CTRL";
	if ((stateMask & SWT.ALT) != 0) string += " ALT";
	if ((stateMask & SWT.SHIFT) != 0) string += " SHIFT";
	if ((stateMask & SWT.COMMAND) != 0) string += " COMMAND";
	return string;
}

public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	Listener listener = new Listener () {
		public void handleEvent (Event e) {
			String string = "Unknown";
			switch (e.type) {
				case SWT.MouseDown: string = "DOWN"; break;
				case SWT.MouseMove: string = "MOVE"; break;
				case SWT.MouseUp: string = "UP"; break;
			}
			string +=": button: " + e.button + ", ";
			string += "stateMask=0x" + Integer.toHexString (e.stateMask) + stateMask (e.stateMask) + ", x=" + e.x + ", y=" + e.y;
			if ((e.stateMask & SWT.BUTTON1) != 0) string += " BUTTON1";
			if ((e.stateMask & SWT.BUTTON2) != 0) string += " BUTTON2";
			if ((e.stateMask & SWT.BUTTON3) != 0) string += " BUTTON3";
			if ((e.stateMask & SWT.BUTTON4) != 0) string += " BUTTON4";
			if ((e.stateMask & SWT.BUTTON5) != 0) string += " BUTTON5";
			System.out.println (string);
		}
	};
	shell.addListener (SWT.MouseDown, listener);
	shell.addListener (SWT.MouseMove, listener);
	shell.addListener (SWT.MouseUp, listener);
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10260, 1, '[SWT]SnippetSnippet63.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Shell example snippet: create a dialog shell (prompt for a value)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet63 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.pack ();
	shell.open ();
	final boolean [] result = new boolean [1];
	final Shell dialog = new Shell (shell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
	dialog.setLayout (new RowLayout ());
	final Button ok = new Button (dialog, SWT.PUSH);
	ok.setText ("OK");
	Button cancel = new Button (dialog, SWT.PUSH);
	cancel.setText ("Cancel");
	Listener listener =new Listener () {
		public void handleEvent (Event event) {
			result [0] = event.widget == ok;
			dialog.close ();
		}
	};
	ok.addListener (SWT.Selection, listener);
	cancel.addListener (SWT.Selection, listener);
	dialog.pack ();
	dialog.open ();
	System.out.println ("Prompt ...");
	while (!dialog.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	System.out.println ("Result: " + result [0]);
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10261, 1, '[SWT]SnippetSnippet64.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Table example snippet: print selected items in a table
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet64 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	final Table table = new Table (shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL);
	for (int i=0; i<16; i++) {
		TableItem item = new TableItem (table, 0);
		item.setText ("Item " + i);
	}
	table.setBounds (0, 0, 100, 100);
	table.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			String string = "";
			TableItem [] selection = table.getSelection ();
			for (int i=0; i<selection.length; i++) string += selection [i] + " ";
			System.out.println ("Selection={" + string + "}");
		}
	});
	table.addListener (SWT.DefaultSelection, new Listener () {
		public void handleEvent (Event e) {
			String string = "";
			TableItem [] selection = table.getSelection ();
			for (int i=0; i<selection.length; i++) string += selection [i] + " ";
			System.out.println ("DefaultSelection={" + string + "}");
		}
	});
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10262, 1, '[SWT]SnippetSnippet65.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * FormLayout example snippet: create a simple dialog using form layout
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet65 {

public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	Label label = new Label (shell, SWT.WRAP);
	label.setText ("This is a long text string that will wrap when the dialog is resized.");
	List list = new List (shell, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
	list.setItems (new String [] {"Item 1", "Item 2"});
	Button button1 = new Button (shell, SWT.PUSH);
	button1.setText ("OK");
	Button button2 = new Button (shell, SWT.PUSH);
	button2.setText ("Cancel");
	
	final int insetX = 4, insetY = 4;
	FormLayout formLayout = new FormLayout ();
	formLayout.marginWidth = insetX;
	formLayout.marginHeight = insetY;
	shell.setLayout (formLayout);
	
	Point size = label.computeSize (SWT.DEFAULT, SWT.DEFAULT);
	final FormData labelData = new FormData (size.x, SWT.DEFAULT);
	labelData.left = new FormAttachment (0, 0);
	labelData.right = new FormAttachment (100, 0);
	label.setLayoutData (labelData);
	shell.addListener (SWT.Resize, new Listener () {
		public void handleEvent (Event e) {
			Rectangle rect = shell.getClientArea ();
			labelData.width = rect.width - insetX * 2;
			shell.layout ();
		}
	});
		
	FormData button2Data = new FormData ();
	button2Data.right = new FormAttachment (100, -insetX);
	button2Data.bottom = new FormAttachment (100, 0);
	button2.setLayoutData (button2Data);
	
	FormData button1Data = new FormData ();
	button1Data.right = new FormAttachment (button2, -insetX);
	button1Data.bottom = new FormAttachment (100, 0);
	button1.setLayoutData (button1Data);
	
	FormData listData = new FormData ();
	listData.left = new FormAttachment (0, 0);
	listData.right = new FormAttachment (100, 0);
	listData.top = new FormAttachment (label, insetY);
	listData.bottom = new FormAttachment (button2, -insetY);
	list.setLayoutData (listData);
	
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10263, 1, '[SWT]SnippetSnippet66.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * GC example snippet: implement a simple scribble program
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet66 {

public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	Listener listener = new Listener () {
		int lastX = 0, lastY = 0;
		public void handleEvent (Event event) {
			switch (event.type) {
				case SWT.MouseMove:
					if ((event.stateMask & SWT.BUTTON1) == 0) break;
					GC gc = new GC (shell);
					gc.drawLine (lastX, lastY, event.x, event.y);
					gc.dispose ();
					//FALL THROUGH
				case SWT.MouseDown:
					lastX = event.x;
					lastY = event.y;
					break;
			}
		}
	};
	shell.addListener (SWT.MouseDown, listener);
	shell.addListener (SWT.MouseMove, listener);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10264, 1, '[SWT]SnippetSnippet67.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * ToolBar example snippet: place a drop down menu in a tool bar
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet67 {

public static void main (String [] args) {
	final Display display = new Display ();
	final Shell shell = new Shell (display);
	final ToolBar toolBar = new ToolBar (shell, SWT.NONE);
	final Menu menu = new Menu (shell, SWT.POP_UP);
	for (int i=0; i<8; i++) {
		MenuItem item = new MenuItem (menu, SWT.PUSH);
		item.setText ("Item " + i);
	}
	final ToolItem item = new ToolItem (toolBar, SWT.DROP_DOWN);
	item.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event event) {
			if (event.detail == SWT.ARROW) {
				Rectangle rect = item.getBounds ();
				Point pt = new Point (rect.x, rect.y + rect.height);
				pt = toolBar.toDisplay (pt);
				menu.setLocation (pt.x, pt.y);
				menu.setVisible (true);
			}
		}
	});
	toolBar.pack ();
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	menu.dispose ();
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10265, 1, '[SWT]SnippetSnippet68.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Display example snippet: stop a repeating timer when a button is pressed
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet68 {

public static void main (String [] args) {
	final Display display = new Display ();
	final Color red = display.getSystemColor (SWT.COLOR_RED);
	final Color blue = display.getSystemColor (SWT.COLOR_BLUE);
	Shell shell = new Shell (display);
	shell.setLayout (new RowLayout ());
	Button button = new Button (shell, SWT.PUSH);
	button.setText ("Stop Timer");
	final Label label = new Label (shell, SWT.BORDER);
	label.setBackground (red);
	final int time = 500;
	final Runnable timer = new Runnable () {
		public void run () {
			if (label.isDisposed ()) return;
			Color color = label.getBackground ().equals (red) ? blue : red;
			label.setBackground (color);
			display.timerExec (time, this);
		}
	};
	display.timerExec (time, timer);
	button.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event event) {
			display.timerExec (-1, timer);
		}
	});
	button.pack ();
	label.setLayoutData (new RowData (button.getSize ()));
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10266, 1, '[SWT]SnippetSnippet69.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * FormLayout example snippet: center a label and single line text using a form layout
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet69 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Label label = new Label (shell, SWT.NONE | SWT.BORDER);
	label.setText ("Name");
	Text text = new Text (shell, SWT.NONE);

	FormLayout layout = new FormLayout ();
	layout.marginWidth = layout.marginHeight = 5;
	shell.setLayout (layout);

	GC gc = new GC (text);
	FontMetrics fm = gc.getFontMetrics ();
	int charWidth = fm.getAverageCharWidth ();
	int width = text.computeSize (charWidth * 8, SWT.DEFAULT).x;
	gc.dispose ();
	FormData data = new FormData (width, SWT.DEFAULT);
	text.setLayoutData (data);
	data.left = new FormAttachment (label, 5);
	data.top = new FormAttachment (label, 0, SWT.CENTER);
	
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10267, 1, '[SWT]SnippetSnippet7.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * example snippet: create a table (lazy)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet7 {
	
public static void main (String [] args) {
	final Display display = new Display ();
	final Image image = new Image (display, 16, 16);
	GC gc = new GC (image);
	gc.setBackground (display.getSystemColor (SWT.COLOR_RED));
	gc.fillRectangle (image.getBounds ());
	gc.dispose ();
	final Shell shell = new Shell (display);
	shell.setText ("Lazy Table");
	shell.setLayout (new FillLayout ());
	final Table table = new Table (shell, SWT.BORDER | SWT.MULTI);
	table.setSize (200, 200);
	Thread thread = new Thread () {
		public void run () {
			for (int i=0; i<20000; i++) {
				if (table.isDisposed ()) return;
				final int [] index = new int [] {i};
				display.syncExec (new Runnable () {
					public void run () {
						if (table.isDisposed ()) return;
						TableItem item = new TableItem (table, SWT.NONE);
						item.setText ("Table Item " + index [0]);
						item.setImage (image);
					}
				});
			}
		}
	};
	thread.start ();
	shell.setSize (200, 200);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	image.dispose ();
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10268, 1, '[SWT]SnippetSnippet70.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * GC example snippet: create an icon (in memory)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet70 {

public static void main (String [] args) {
	Display display = new Display ();
	Color red = display.getSystemColor (SWT.COLOR_RED);
	Color white = display.getSystemColor (SWT.COLOR_WHITE);
	Color black = display.getSystemColor (SWT.COLOR_BLACK);
	
	Image image = new Image (display, 20, 20);
	GC gc = new GC (image);
	gc.setBackground (red);
	gc.fillRectangle (5, 5, 10, 10);
	gc.dispose ();
	ImageData imageData = image.getImageData ();
	
	PaletteData palette = new PaletteData (
		new RGB [] {
			new RGB (0, 0, 0),
			new RGB (0xFF, 0xFF, 0xFF),
		});
	ImageData maskData = new ImageData (20, 20, 1, palette);
	Image mask = new Image (display, maskData);
	gc = new GC (mask);
	gc.setBackground (black);
	gc.fillRectangle (0, 0, 20, 20);
	gc.setBackground (white);
	gc.fillRectangle (5, 5, 10, 10);
	gc.dispose ();
	maskData = mask.getImageData ();
	
	Image icon = new Image (display, imageData, maskData);
	Shell shell = new Shell (display);
	Button button = new Button (shell, SWT.PUSH);
	button.setImage (icon);
	button.setSize (60, 60);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	icon.dispose ();
	image.dispose ();
	mask.dispose ();
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10269, 1, '[SWT]SnippetSnippet71.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * FormLayout example snippet: create a simple OK/CANCEL dialog using form layout
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet71 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.pack ();
	shell.open ();
	Shell dialog = new Shell (shell, SWT.DIALOG_TRIM);
	Label label = new Label (dialog, SWT.NONE);
	label.setText ("Exit the application?");
	Button okButton = new Button (dialog, SWT.PUSH);
	okButton.setText ("&OK");
	Button cancelButton = new Button (dialog, SWT.PUSH);
	cancelButton.setText ("&Cancel");
	
	FormLayout form = new FormLayout ();
	form.marginWidth = form.marginHeight = 8;
	dialog.setLayout (form);
	FormData okData = new FormData ();
	okData.top = new FormAttachment (label, 8);
	okButton.setLayoutData (okData);
	FormData cancelData = new FormData ();
	cancelData.left = new FormAttachment (okButton, 8);
	cancelData.top = new FormAttachment (okButton, 0, SWT.TOP);
	cancelButton.setLayoutData (cancelData);
	
	dialog.setDefaultButton (okButton);
	dialog.pack ();
	dialog.open ();
	
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10270, 1, '[SWT]SnippetSnippet72.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * FileDialog example snippet: prompt for a file name (to save)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet72 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.open ();
	FileDialog dialog = new FileDialog (shell, SWT.SAVE);
	dialog.setFilterNames (new String [] {"Batch Files", "All Files (*.*)"});
	dialog.setFilterExtensions (new String [] {"*.bat", "*.*"}); //Windows wild cards
	dialog.setFilterPath ("c:\\"); //Windows path
	dialog.setFileName ("fred.bat");
	System.out.println ("Save to: " + dialog.open ());
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10271, 1, '[SWT]SnippetSnippet73.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Menu example snippet: enable menu items dynamically (when menu shown)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet73 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	final Tree tree = new Tree (shell, SWT.BORDER | SWT.MULTI);
	final Menu menu = new Menu (shell, SWT.POP_UP);
	tree.setMenu (menu);
	for (int i=0; i<12; i++) {
		TreeItem treeItem = new TreeItem (tree, SWT.NONE);
		treeItem.setText ("Item " + i);
		MenuItem menuItem = new MenuItem (menu, SWT.PUSH);
		menuItem.setText (treeItem.getText ());
	}
	menu.addListener (SWT.Show, new Listener () {
		public void handleEvent (Event event) {
			MenuItem [] menuItems = menu.getItems ();
			TreeItem [] treeItems = tree.getSelection ();
			for (int i=0; i<menuItems.length; i++) {
				String text = menuItems [i].getText ();
				int index = 0;
				while (index<treeItems.length) {
					if (treeItems [index].getText ().equals (text)) break;
					index++;
				}
				menuItems [i].setEnabled (index != treeItems.length);
			}
		}
	});
	tree.setSize (200, 200);
	shell.setSize (300, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10272, 1, '[SWT]SnippetSnippet74.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Caret example snippet: create a caret
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet74 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Caret caret = new Caret (shell, SWT.NONE);
	caret.setBounds (10, 10, 2, 32);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10273, 1, '[SWT]SnippetSnippet75.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Composite example snippet: set the tab traversal order of children
 * In this example, composite1 (i.e. c1) tab order is set to: B2, B1, B3, and
 * shell tab order is set to: c1, B7, toolBar1, (c4: no focusable children), c2, L2
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet75 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new RowLayout ());
	
	Composite c1 = new Composite (shell, SWT.BORDER);
	c1.setLayout (new RowLayout ());
	Button b1 = new Button (c1, SWT.PUSH);
	b1.setText ("B&1");
	Button r1 = new Button (c1, SWT.RADIO);
	r1.setText ("R1");
	Button r2 = new Button (c1, SWT.RADIO);
	r2.setText ("R&2");
	Button r3 = new Button (c1, SWT.RADIO);
	r3.setText ("R3");
	Button b2 = new Button (c1, SWT.PUSH);
	b2.setText ("B2");
	List l1 = new List (c1, SWT.SINGLE | SWT.BORDER);
	l1.setItems (new String [] {"L1"});
	Button b3 = new Button (c1, SWT.PUSH);
	b3.setText ("B&3");
	Button b4 = new Button (c1, SWT.PUSH);
	b4.setText ("B&4");
	
	Composite c2 = new Composite (shell, SWT.BORDER);
	c2.setLayout (new RowLayout ());
	Button b5 = new Button (c2, SWT.PUSH);
	b5.setText ("B&5");
	Button b6 = new Button (c2, SWT.PUSH);
	b6.setText ("B&6");
	
	List l2 = new List (shell, SWT.SINGLE | SWT.BORDER);
	l2.setItems (new String [] {"L2"});
	
	ToolBar tb1 = new ToolBar (shell, SWT.FLAT | SWT.BORDER);
	ToolItem i1 = new ToolItem (tb1, SWT.RADIO);
	i1.setText ("I1");
	ToolItem i2 = new ToolItem (tb1, SWT.RADIO);
	i2.setText ("I2");
	Combo combo1 = new Combo (tb1, SWT.READ_ONLY | SWT.BORDER);
	combo1.setItems (new String [] {"C1"});
	combo1.setText ("C1");
	combo1.pack ();
	ToolItem i3 = new ToolItem (tb1, SWT.SEPARATOR);
	i3.setWidth (combo1.getSize ().x);
	i3.setControl (combo1);
	ToolItem i4 = new ToolItem (tb1, SWT.PUSH);
	i4.setText ("I&4");
	ToolItem i5 = new ToolItem (tb1, SWT.CHECK);
	i5.setText ("I5");
	
	Button b7 = new Button (shell, SWT.PUSH);
	b7.setText ("B&7");
	
	Composite c4 = new Composite (shell, SWT.BORDER);
	Composite c5 = new Composite (c4, SWT.BORDER);
	c5.setLayout(new FillLayout());
	new Label(c5, SWT.NONE).setText("No");
	c5.pack();

	Control [] tabList1 = new Control [] {b2, b1, b3};
	c1.setTabList (tabList1);
	Control [] tabList2 = new Control [] {c1, b7, tb1, c4, c2, l2};
	shell.setTabList (tabList2);

	shell.pack ();
	shell.open ();
	
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10274, 1, '[SWT]SnippetSnippet76.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * TabFolder example snippet: create a tab folder (six pages)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet76 {

public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	final TabFolder tabFolder = new TabFolder (shell, SWT.BORDER);
	for (int i=0; i<6; i++) {
		TabItem item = new TabItem (tabFolder, SWT.NONE);
		item.setText ("TabItem " + i);
		Button button = new Button (tabFolder, SWT.PUSH);
		button.setText ("Page " + i);
		item.setControl (button);
	}
	tabFolder.pack ();
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10275, 1, '[SWT]SnippetSnippet77.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Table example snippet: resize columns as table resizes
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet77 {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
		
	final Composite comp = new Composite(shell, SWT.NONE);
	final Table table = new Table(comp, SWT.BORDER | SWT.V_SCROLL);
	table.setHeaderVisible(true);
	table.setLinesVisible(true);
	final TableColumn column1 = new TableColumn(table, SWT.NONE);
	column1.setText("Column 1");
	final TableColumn column2 = new TableColumn(table, SWT.NONE);
	column2.setText("Column 2");
	for (int i = 0; i < 10; i++) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[] {"item 0" + i, "item 1"+i});
	}
	comp.addControlListener(new ControlAdapter() {
		public void controlResized(ControlEvent e) {
			Rectangle area = comp.getClientArea();
			Point size = table.computeSize(SWT.DEFAULT, SWT.DEFAULT);
			ScrollBar vBar = table.getVerticalBar();
			int width = area.width - table.computeTrim(0,0,0,0).width - vBar.getSize().x;
			if (size.y > area.height + table.getHeaderHeight()) {
				// Subtract the scrollbar width from the total column width
				// if a vertical scrollbar will be required
				Point vBarSize = vBar.getSize();
				width -= vBarSize.x;
			}
			Point oldSize = table.getSize();
			if (oldSize.x > area.width) {
				// table is getting smaller so make the columns 
				// smaller first and then resize the table to
				// match the client area width
				column1.setWidth(width/3);
				column2.setWidth(width - column1.getWidth());
				table.setSize(area.width, area.height);
			} else {
				// table is getting bigger so make the table 
				// bigger first and then make the columns wider
				// to match the client area width
				table.setSize(area.width, area.height);
				column1.setWidth(width/3);
				column2.setWidth(width - column1.getWidth());
			}
		}
	});
		
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10276, 1, '[SWT]SnippetSnippet78.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Drag and Drop example snippet: drag text between two labels
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet78 {

public static void main (String [] args) {
	
	Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	final Label label1 = new Label (shell, SWT.BORDER);
	label1.setText ("TEXT");
	final Label label2 = new Label (shell, SWT.BORDER);
	setDragDrop (label1);
	setDragDrop (label2);
	shell.setSize (200, 200);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
public static void setDragDrop (final Label label) {
	
	Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
	int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
	
	final DragSource source = new DragSource (label, operations);
	source.setTransfer(types);
	source.addDragListener (new DragSourceListener () {
		public void dragStart(DragSourceEvent event) {
			event.doit = (label.getText ().length () != 0);
		}
		public void dragSetData (DragSourceEvent event) {
			event.data = label.getText ();
		}
		public void dragFinished(DragSourceEvent event) {
			if (event.detail == DND.DROP_MOVE)
				label.setText ("");
		}
	});

	DropTarget target = new DropTarget(label, operations);
	target.setTransfer(types);
	target.addDropListener (new DropTargetAdapter() {
		public void drop(DropTargetEvent event) {
			if (event.data == null) {
				event.detail = DND.DROP_NONE;
				return;
			}
			label.setText ((String) event.data);
		}
	});
}
}
', now(), now());
insert into SNIPPET values (10277, 1, '[SWT]SnippetSnippet79.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Drag and Drop example snippet: define my own data transfer type
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.1
 */
import java.io.*;

import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet79 {

/* The data being transferred is an <bold>array of type MyType</bold> where MyType is define as: */
static class MyType {
	String fileName;
	long fileLength;
	long lastModified;
}

static class MyTransfer extends ByteArrayTransfer {

	private static final String MYTYPENAME = "name_for_my_type";
	private static final int MYTYPEID = registerType (MYTYPENAME);
	private static MyTransfer _instance = new MyTransfer ();

public static MyTransfer getInstance () {
	return _instance;
}

public void javaToNative (Object object, TransferData transferData) {
	if (!checkMyType(object) || !isSupportedType (transferData)) {
		DND.error(DND.ERROR_INVALID_DATA);
	}
	MyType [] myTypes = (MyType []) object;
	try {
		// write data to a byte array and then ask super to convert to pMedium
		ByteArrayOutputStream out = new ByteArrayOutputStream ();
		DataOutputStream writeOut = new DataOutputStream (out);
		for (int i = 0, length = myTypes.length; i < length; i++) {
			byte [] buffer = myTypes [i].fileName.getBytes ();
			writeOut.writeInt (buffer.length);
			writeOut.write (buffer);
			writeOut.writeLong (myTypes [i].fileLength);
			writeOut.writeLong (myTypes [i].lastModified);
		}
		byte [] buffer = out.toByteArray ();
		writeOut.close ();
		super.javaToNative (buffer, transferData);
	}
	catch (IOException e) {}
}

public Object nativeToJava (TransferData transferData) {
	if (isSupportedType (transferData)) {
		byte [] buffer = (byte []) super.nativeToJava (transferData);
		if (buffer == null) return null;

		MyType [] myData = new MyType [0];
		try {
			ByteArrayInputStream in = new ByteArrayInputStream (buffer);
			DataInputStream readIn = new DataInputStream (in);
			while (readIn.available () > 20) {
				MyType datum = new MyType ();
				int size = readIn.readInt ();
				byte [] name = new byte [size];
				readIn.read (name);
				datum.fileName = new String (name);
				datum.fileLength = readIn.readLong ();
				datum.lastModified = readIn.readLong ();
				MyType [] newMyData = new MyType [myData.length + 1];
				System.arraycopy (myData, 0, newMyData, 0, myData.length);
				newMyData [myData.length] = datum;
				myData = newMyData;
			}
			readIn.close ();
		}
		catch (IOException ex) {
			return null;
		}
		return myData;
	}

	return null;
}

protected String [] getTypeNames () {
	return new String [] {MYTYPENAME};
}

protected int [] getTypeIds () {
	return new int [] {MYTYPEID};
}

boolean checkMyType(Object object) {
	if (object == null || 
		!(object instanceof MyType[]) || 
		((MyType[])object).length == 0) {
		return false;
	}
	MyType[] myTypes = (MyType[])object;
	for (int i = 0; i < myTypes.length; i++) {
		if (myTypes[i] == null || 
			myTypes[i].fileName == null || 
			myTypes[i].fileName.length() == 0) {
			return false;
		}
	}
	return true;
}

protected boolean validate(Object object) {
	return checkMyType(object);
}
}

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new FillLayout ());
	final Label label1 = new Label (shell, SWT.BORDER | SWT.WRAP);
	label1.setText ("Drag Source for MyData[]");
	final Label label2 = new Label (shell, SWT.BORDER | SWT.WRAP);
	label2.setText ("Drop Target for MyData[]");

	DragSource source = new DragSource (label1, DND.DROP_COPY);
	source.setTransfer (new Transfer [] {MyTransfer.getInstance ()});
	source.addDragListener (new DragSourceAdapter () {
		public void dragSetData (DragSourceEvent event) {
			MyType myType1 = new MyType ();
			myType1.fileName = "C:\\abc.txt";
			myType1.fileLength = 1000;
			myType1.lastModified = 12312313;
			MyType myType2 = new MyType ();
			myType2.fileName = "C:\\xyz.txt";
			myType2.fileLength = 500;
			myType2.lastModified = 12312323;
			event.data = new MyType [] {myType1, myType2};
		}
	});
	DropTarget target = new DropTarget (label2, DND.DROP_COPY | DND.DROP_DEFAULT);
	target.setTransfer (new Transfer [] {MyTransfer.getInstance ()});
	target.addDropListener (new DropTargetAdapter () {
		public void dragEnter (DropTargetEvent event) {
			if (event.detail == DND.DROP_DEFAULT) {
				event.detail = DND.DROP_COPY;
			}
		}

		public void dragOperationChanged (DropTargetEvent event) {
			if (event.detail == DND.DROP_DEFAULT) {
				event.detail = DND.DROP_COPY;
			}
		}

		public void drop (DropTargetEvent event) {
			if (event.data != null) {
				MyType [] myTypes = (MyType []) event.data;
				if (myTypes != null) {
					String string = "";
					for (int i = 0; i < myTypes.length; i++) {
						string += myTypes [i].fileName + " ";
					}
					label2.setText (string);
				}
			}
		}

	});
	shell.setSize (200, 200);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}', now(), now());
insert into SNIPPET values (10278, 1, '[SWT]SnippetSnippet8.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Tree example snippet: create a tree (lazy)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

import java.io.*;

public class Snippet8 {
	
public static void main (String [] args) {
	final Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setText ("Lazy Tree");
	shell.setLayout (new FillLayout ());
	final Tree tree = new Tree (shell, SWT.BORDER);
	File [] roots = File.listRoots ();
	for (int i=0; i<roots.length; i++) {
		TreeItem root = new TreeItem (tree, 0);
		root.setText (roots [i].toString ());
		root.setData (roots [i]);
		new TreeItem (root, 0);
	}
	tree.addListener (SWT.Expand, new Listener () {
		public void handleEvent (final Event event) {
			final TreeItem root = (TreeItem) event.item;
			TreeItem [] items = root.getItems ();
			for (int i= 0; i<items.length; i++) {
				if (items [i].getData () != null) return;
				items [i].dispose ();
			}
			File file = (File) root.getData ();
			File [] files = file.listFiles ();
			if (files == null) return;
			for (int i= 0; i<files.length; i++) {
				TreeItem item = new TreeItem (root, 0);
				item.setText (files [i].getName ());
				item.setData (files [i]);
				if (files [i].isDirectory()) {
					new TreeItem (item, 0);
				}
			}
		}
	});
	Point size = tree.computeSize (300, SWT.DEFAULT);
	int width = Math.max (300, size.x);
	int height = Math.max (300, size.y);
	shell.setSize (shell.computeSize (width, height));
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10279, 1, '[SWT]SnippetSnippet80.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tree example snippet: limit selection to items that match a pattern
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet80 {
	
public static void main(String[] args) {
	final Display display = new Display();
	final Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	final Tree tree = new Tree(shell, SWT.BORDER | SWT.MULTI);
	for (int i = 0; i < 2; i++) {
		TreeItem item = new TreeItem(tree, SWT.NONE);
		item.setText("item " + i);
		for (int j = 0; j < 2; j++) {
			TreeItem subItem = new TreeItem(item, SWT.NONE);
			subItem.setText("item " + j);
			for (int k = 0; k < 2; k++) {
				TreeItem subsubItem = new TreeItem(subItem, SWT.NONE);
				subsubItem.setText("item " + k);
			}
		}
	}
	
	tree.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			TreeItem[] selection = tree.getSelection();
			TreeItem[] revisedSelection = new TreeItem[0];
			for (int i = 0; i < selection.length; i++) {
				String text = selection[i].getText();
				if (text.indexOf("1") > 0) {
					TreeItem[] newSelection = new TreeItem[revisedSelection.length + 1];
					System.arraycopy(revisedSelection, 0, newSelection, 0, revisedSelection.length);
					newSelection[revisedSelection.length] = selection[i];
					revisedSelection = newSelection;
				}
			}
			tree.setSelection(revisedSelection);
		}
	});

	shell.setSize(300, 300);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10280, 1, '[SWT]SnippetSnippet81.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * OLE and ActiveX example snippet: browse the typelibinfo for a program id (win32 only)
 * NOTE: This snippet uses internal SWT packages that are
 * subject to change without notice.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.internal.ole.win32.*;
import org.eclipse.swt.ole.win32.*;
import org.eclipse.swt.widgets.*;

public class Snippet81 {
	
public static void main(String[] args) {
	
	if (args.length == 0) {
		System.out.println("Usage: java Main <program id>");
		return;
	}

	String progID = args[0];
	
	Display display = new Display();
	Shell shell = new Shell(display);

	OleFrame frame = new OleFrame(shell, SWT.NONE);
	OleControlSite site = null;
	OleAutomation auto = null;
	try {
		site = new OleControlSite(frame, SWT.NONE, progID);	
		auto = new OleAutomation(site);
	} catch (SWTException ex) {
		System.out.println("Unable to open type library for "+progID);
		return;
	}
		
	printTypeInfo(auto);
	
	auto.dispose();
	shell.dispose();
	display.dispose();

}

private static void printTypeInfo(OleAutomation auto) {
	TYPEATTR typeattr = auto.getTypeInfoAttributes();
	if (typeattr != null) {		
		if (typeattr.cFuncs > 0) System.out.println("Functions :\n");
		for (int i = 0; i < typeattr.cFuncs; i++) {			
			OleFunctionDescription data = auto.getFunctionDescription(i);
			String argList = "";
			int firstOptionalArgIndex = data.args.length - data.optionalArgCount;
			for (int j = 0; j < data.args.length; j++) {
				argList += "[";
				if (j >= firstOptionalArgIndex) argList += "optional, ";
				argList += getDirection(data.args[j].flags)+"] "+getTypeName(data.args[j].type)+" "+data.args[j].name;
				if ( j < data.args.length - 1) argList += ", ";
			}			
			System.out.println(getInvokeKind(data.invokeKind)+" (id = "+data.id+") : "
					        +"\n\tSignature   : "+getTypeName(data.returnType)+" "+data.name+"("+argList+")"
			                    +"\n\tDescription : "+data.documentation
			                    +"\n\tHelp File   : "+data.helpFile+"\n");
		}
		
		if (typeattr.cVars > 0) System.out.println("\n\nVariables  :\n");
		for (int i = 0; i < typeattr.cVars; i++) {
			OlePropertyDescription data = auto.getPropertyDescription(i);
			System.out.println("PROPERTY (id = "+data.id+") :"
			                    +"\n\tName : "+data.name
			                    +"\n\tType : "+getTypeName(data.type)+"\n");
		}
	}
}
private static String getTypeName(int type) {
	switch (type) {
		case OLE.VT_BOOL : return "boolean";
		case OLE.VT_R4 : return "float";
		case OLE.VT_R8 : return "double";
		case OLE.VT_I4 : return "int";
		case OLE.VT_DISPATCH : return "IDispatch";
		case OLE.VT_UNKNOWN : return "IUnknown";
		case OLE.VT_I2 : return "short";
		case OLE.VT_BSTR : return "String";
		case OLE.VT_VARIANT : return "Variant";
		case OLE.VT_CY : return "Currency";
		case OLE.VT_DATE : return "Date";
		case OLE.VT_UI1 : return "unsigned char";
		case OLE.VT_UI4 : return "unsigned int";
		case OLE.VT_USERDEFINED : return "UserDefined";
		case OLE.VT_HRESULT : return "int";
		case OLE.VT_VOID : return "void";
		
		case OLE.VT_BYREF | OLE.VT_BOOL : return "boolean *";
		case OLE.VT_BYREF | OLE.VT_R4 : return "float *";
		case OLE.VT_BYREF | OLE.VT_R8 : return "double *";
		case OLE.VT_BYREF | OLE.VT_I4 : return "int *";
		case OLE.VT_BYREF | OLE.VT_DISPATCH : return "IDispatch *";
		case OLE.VT_BYREF | OLE.VT_UNKNOWN : return "IUnknown *";
		case OLE.VT_BYREF | OLE.VT_I2 : return "short *";
		case OLE.VT_BYREF | OLE.VT_BSTR : return "String *";
		case OLE.VT_BYREF | OLE.VT_VARIANT : return "Variant *";
		case OLE.VT_BYREF | OLE.VT_CY : return "Currency *";
		case OLE.VT_BYREF | OLE.VT_DATE : return "Date *";
		case OLE.VT_BYREF | OLE.VT_UI1 : return "unsigned char *";
		case OLE.VT_BYREF | OLE.VT_UI4 : return "unsigned int *";
		case OLE.VT_BYREF | OLE.VT_USERDEFINED : return "UserDefined *";
	}
	return "unknown "+ type;	
}
private static String getDirection(int direction){
	String dirString = "";
	boolean comma = false;
	if ((direction & OLE.IDLFLAG_FIN) != 0) {
		dirString += "in";
		comma = true;
	}
	if ((direction & OLE.IDLFLAG_FOUT) != 0){
		if (comma) dirString += ", ";
		dirString += "out";
		comma = true;
	}
	if ((direction & OLE.IDLFLAG_FLCID) != 0){
		if (comma) dirString += ", ";
		dirString += "lcid";
		comma = true;
	}
	if ((direction & OLE.IDLFLAG_FRETVAL) != 0){
		if (comma) dirString += ", "; 
		dirString += "retval";
	}
	
	return dirString;
}
private static String getInvokeKind(int invKind) {
	switch (invKind) {
		case OLE.INVOKE_FUNC : return "METHOD";
		case OLE.INVOKE_PROPERTYGET : return "PROPERTY GET";
		case OLE.INVOKE_PROPERTYPUT : return "PROPERTY PUT";
		case OLE.INVOKE_PROPERTYPUTREF : return "PROPERTY PUT BY REF";
	}
	return "unknown "+invKind;
}
}
', now(), now());
insert into SNIPPET values (10281, 1, '[SWT]SnippetSnippet82.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * CTabFolder example snippet: prevent an item from closing
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 * 
 * @since 3.0
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet82 {
	
public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	CTabFolder folder = new CTabFolder(shell, SWT.BORDER);
	for (int i = 0; i < 4; i++) {
		CTabItem item = new CTabItem(folder, SWT.CLOSE);
		item.setText("Item "+i);
		Text text = new Text(folder, SWT.MULTI);
		text.setText("Content for Item "+i);
		item.setControl(text);
	}
	
	final CTabItem specialItem = new CTabItem(folder, SWT.CLOSE);
	specialItem.setText("Dont Close Me");
	Text text = new Text(folder, SWT.MULTI);
	text.setText("This tab can never be closed");
	specialItem.setControl(text);
		
	folder.addCTabFolder2Listener(new CTabFolder2Adapter() {
		public void close(CTabFolderEvent event) {
			if (event.item.equals(specialItem)) {
				event.doit = false;
			}
		}
	});
	
	final CTabItem noCloseItem = new CTabItem(folder, SWT.NONE);
	noCloseItem.setText("No Close Button");
	Text text2 = new Text(folder, SWT.MULTI);
	text2.setText("This tab does not have a close button");
	noCloseItem.setControl(text2);
	
	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10282, 1, '[SWT]SnippetSnippet83.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Drag and Drop example snippet: determine data types available (win32 only)
 * NOTE: This snippet uses internal SWT packages that are
 * subject to change without notice.
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.internal.ole.win32.*;
import org.eclipse.swt.internal.win32.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet83 extends ByteArrayTransfer {

private static Snippet83 _instance = new Snippet83();
private int[] ids;
private String[] names;
	
public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	final Table control = new Table(shell, SWT.NONE);
	TableItem item = new TableItem(control, SWT.NONE);
	item.setText("Drag data over this site to see the native transfer type.");
	DropTarget target = new DropTarget(control, DND.DROP_DEFAULT | DND.DROP_COPY | DND.DROP_LINK | DND.DROP_MOVE);
	target.setTransfer(new Transfer[] {Snippet83.getInstance()});
	target.addDropListener(new DropTargetAdapter() {
		public void dragEnter(DropTargetEvent event) {			
			String ops = "";
			if ((event.operations & DND.DROP_COPY) != 0) ops += "Copy;";
			if ((event.operations & DND.DROP_MOVE) != 0) ops += "Move;";
			if ((event.operations & DND.DROP_LINK) != 0) ops += "Link;";
			control.removeAll();
			TableItem item1 = new TableItem(control,SWT.NONE);
			item1.setText("Allowed Operations are "+ops);
			
			if (event.detail == DND.DROP_DEFAULT) {
				if ((event.operations & DND.DROP_COPY) != 0) {
					event.detail = DND.DROP_COPY;
				} else if ((event.operations & DND.DROP_LINK) != 0) {
					event.detail = DND.DROP_LINK;
				} else if ((event.operations & DND.DROP_MOVE) != 0) {
					event.detail = DND.DROP_MOVE;
				}
			}
			
			TransferData[] data = event.dataTypes;
			for (int i = 0; i < data.length; i++) {
				int id = data[i].type;
				String name = getNameFromId(id);
				TableItem item2 = new TableItem(control,SWT.NONE);
				item2.setText("Data type is "+id+" "+name);
			}
		}
	});
	
	shell.setSize(400, 400);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}

public static Snippet83 getInstance () {
	return _instance;
}
Snippet83() {
	ids = new int[80000];
	names = new String[80000];
	for (int i = 0; i < ids.length; i++) {
		ids[i] = i;
		names[i] = getNameFromId(i);
	}
}
public void javaToNative (Object object, TransferData transferData) {
}
public Object nativeToJava(TransferData transferData){
	return "Hello World";
}
protected String[] getTypeNames(){
	return names;
}
protected int[] getTypeIds(){
	return ids;
}
static String getNameFromId(int id) {
	String name = null;
	int maxSize = 128;
	TCHAR buffer = new TCHAR(0, maxSize);
	int size = COM.GetClipboardFormatName(id, buffer, maxSize);
	if (size != 0) {
		name = buffer.toString(0, size);
	} else {
		switch (id) {
			case COM.CF_HDROP:
				name = "CF_HDROP";
				break;
			case COM.CF_TEXT:
				name = "CF_TEXT";
				break;
			case COM.CF_BITMAP:
				name = "CF_BITMAP";
				break;
			case COM.CF_METAFILEPICT:
				name = "CF_METAFILEPICT";
				break;
			case COM.CF_SYLK:
				name = "CF_SYLK";
				break;
			case COM.CF_DIF:
				name = "CF_DIF";
				break;
			case COM.CF_TIFF:
				name = "CF_TIFF";
				break;
			case COM.CF_OEMTEXT:
				name = "CF_OEMTEXT";
				break;
			case COM.CF_DIB:
				name = "CF_DIB";
				break;
			case COM.CF_PALETTE:
				name = "CF_PALETTE";
				break;
			case COM.CF_PENDATA:
				name = "CF_PENDATA";
				break;
			case COM.CF_RIFF:
				name = "CF_RIFF";
				break;
			case COM.CF_WAVE:
				name = "CF_WAVE";
				break;
			case COM.CF_UNICODETEXT:
				name = "CF_UNICODETEXT";
				break;
			case COM.CF_ENHMETAFILE:
				name = "CF_ENHMETAFILE";
				break;
			case COM.CF_LOCALE:
				name = "CF_LOCALE";
				break;
			case COM.CF_MAX:
				name = "CF_MAX";
				break;
		}
		
	}
	return name;
}
}
', now(), now());
insert into SNIPPET values (10283, 1, '[SWT]SnippetSnippet84.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Drag and Drop example snippet: define a default operation (in this example, Copy)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet84 {
	
public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	
	final Label label = new Label(shell, SWT.BORDER);
	label.setText("Drag Source");
	DragSource source = new DragSource(label, DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK);
	source.setTransfer(new Transfer[] {TextTransfer.getInstance()});
	source.addDragListener(new DragSourceAdapter() {
		public void dragSetData(DragSourceEvent event) {
			event.data = "Text Transferred";
		}
		public void dragFinished(DragSourceEvent event) {
			if (event.doit) {
				String operation = null;
				switch (event.detail) {
				case DND.DROP_MOVE:
					operation = "moved"; break;
				case DND.DROP_COPY:
					operation = "copied"; break;
				case DND.DROP_LINK:
					operation = "linked"; break;
				case DND.DROP_NONE:
					operation = "disallowed"; break;
				default:
					operation = "unknown"; break;
				}
				label.setText("Drag Source (data "+operation+")");
			} else {
				label.setText("Drag Source (drag cancelled)");
			}
		}
	});

	final Text text = new Text(shell, SWT.BORDER | SWT.MULTI);
	text.setText("Drop Target");
	DropTarget target = new DropTarget(text, DND.DROP_DEFAULT | DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_LINK);
	target.setTransfer(new Transfer[] {TextTransfer.getInstance()});
	target.addDropListener(new DropTargetAdapter() {
		public void dragEnter(DropTargetEvent event) {
			if (event.detail == DND.DROP_DEFAULT) event.detail = DND.DROP_COPY;
		}
		public void dragOperationChanged(DropTargetEvent event) {
			if (event.detail == DND.DROP_DEFAULT) event.detail = DND.DROP_COPY;
		}
		public void drop(DropTargetEvent event) {
			String operation = null;
			switch (event.detail) {
			case DND.DROP_MOVE:
				operation = "moved"; break;
			case DND.DROP_COPY:
				operation = "copied"; break;
			case DND.DROP_LINK:
				operation = "linked"; break;
			case DND.DROP_NONE:
				operation = "disallowed"; break;
			default:
				operation = "unknown"; break;
			}
			text.append("\n"+operation+(String)event.data);
		}
	});
	
	shell.setSize(400, 400);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10284, 1, '[SWT]SnippetSnippet85.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * PocketPC example snippet: Hello World
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet85 {

public static void main(String[] args) {
	Display display = new Display();

	/* 
	 * Create a Shell with the default style
	 * i.e. full screen, no decoration on PocketPC.
	 */
	Shell shell = new Shell(display);

	/* 
	 * Set a text so that the top level Shell
	 * also appears in the PocketPC task list
	 */
	shell.setText("Main");

	/*
	 * Set a menubar to follow UI guidelines
	 * on PocketPC
	 */
	Menu mb = new Menu(shell, SWT.BAR);
	shell.setMenuBar(mb);

	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10285, 1, '[SWT]SnippetSnippet86.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * PocketPC Shell example snippet: Ok button
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet86 {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display, SWT.CLOSE);
	shell.setText("Main");
	Menu mb = new Menu(shell, SWT.BAR);
	shell.setMenuBar(mb);
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10286, 1, '[SWT]SnippetSnippet87.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * PocketPC Shell example snippet: resize automatically as SIP is on or off
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet87 {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display, SWT.RESIZE);
	shell.setText("Main");
	Menu mb = new Menu(shell, SWT.BAR);
	shell.setMenuBar(mb);
	/* Add a button to make the resize more visual */
	FillLayout layout = new FillLayout();
	shell.setLayout(layout);
	Button button = new Button(shell, SWT.PUSH);
	button.setText("Main");
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10287, 1, '[SWT]SnippetSnippet88.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * TableEditor example snippet: edit the text of a table item (in place)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet88 {
	
public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new FillLayout());
	final Table table = new Table(shell, SWT.FULL_SELECTION | SWT.HIDE_SELECTION);
	TableColumn column1 = new TableColumn(table, SWT.NONE);
	TableColumn column2 = new TableColumn(table, SWT.NONE);
	for (int i = 0; i < 10; i++) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[] {"item " + i, "edit this value"});
	}
	column1.pack();
	column2.pack();
	
	final TableEditor editor = new TableEditor(table);
	//The editor must have the same size as the cell and must
	//not be any smaller than 50 pixels.
	editor.horizontalAlignment = SWT.LEFT;
	editor.grabHorizontal = true;
	editor.minimumWidth = 50;
	// editing the second column
	final int EDITABLECOLUMN = 1;
	
	table.addSelectionListener(new SelectionAdapter() {
		public void widgetSelected(SelectionEvent e) {
			// Clean up any previous editor control
			Control oldEditor = editor.getEditor();
			if (oldEditor != null) oldEditor.dispose();
	
			// Identify the selected row
			TableItem item = (TableItem)e.item;
			if (item == null) return;
	
			// The control that will be the editor must be a child of the Table
			Text newEditor = new Text(table, SWT.NONE);
			newEditor.setText(item.getText(EDITABLECOLUMN));
			newEditor.addModifyListener(new ModifyListener() {
				public void modifyText(ModifyEvent me) {
					Text text = (Text)editor.getEditor();
					editor.getItem().setText(EDITABLECOLUMN, text.getText());
				}
			});
			newEditor.selectAll();
			newEditor.setFocus();
			editor.setEditor(newEditor, item, EDITABLECOLUMN);
		}
	});
	shell.setSize(300, 300);
	shell.open();
	
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}

}
', now(), now());
insert into SNIPPET values (10288, 1, '[SWT]SnippetSnippet89.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Menu example snippet: create a menu with radio items
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.*;

public class Snippet89 {
public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	Menu menu = new Menu (shell, SWT.POP_UP);
	for (int i=0; i<4; i++) {
		MenuItem item = new MenuItem (menu, SWT.RADIO);
		item.setText ("Item " + i);
		item.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				MenuItem item = (MenuItem)e.widget;
				if (item.getSelection ()) {
					System.out.println (item + " selected");
				} else {
					System.out.println (item + " unselected");
				}
			}
		});
	}
	shell.setMenu (menu);
	shell.setSize (300, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10289, 1, '[SWT]SnippetSnippet9.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Composite example snippet: scroll a child control automatically
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet9 {
	
public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display, SWT.SHELL_TRIM | SWT.H_SCROLL | SWT.V_SCROLL);
	final Composite composite = new Composite (shell, SWT.BORDER);
	composite.setSize (700, 600);
	final Color red = display.getSystemColor (SWT.COLOR_RED);
	composite.addPaintListener (new PaintListener() {
		public void paintControl (PaintEvent e) {
			e.gc.setBackground (red);
			e.gc.fillOval (5, 5, 690, 590);
		}
	});
	final ScrollBar hBar = shell.getHorizontalBar ();
	hBar.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			Point location = composite.getLocation ();
			location.x = -hBar.getSelection ();
			composite.setLocation (location);
		}
	});
	final ScrollBar vBar = shell.getVerticalBar ();
	vBar.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			Point location = composite.getLocation ();
			location.y = -vBar.getSelection ();
			composite.setLocation (location);
		}
	});
	shell.addListener (SWT.Resize,  new Listener () {
		public void handleEvent (Event e) {
			Point size = composite.getSize ();
			Rectangle rect = shell.getClientArea ();
			hBar.setMaximum (size.x);
			vBar.setMaximum (size.y);
			hBar.setThumb (Math.min (size.x, rect.width));
			vBar.setThumb (Math.min (size.y, rect.height));
			int hPage = size.x - rect.width;
			int vPage = size.y - rect.height;
			int hSelection = hBar.getSelection ();
			int vSelection = vBar.getSelection ();
			Point location = composite.getLocation ();
			if (hSelection >= hPage) {
				if (hPage <= 0) hSelection = 0;
				location.x = -hSelection;
			}
			if (vSelection >= vPage) {
				if (vPage <= 0) vSelection = 0;
				location.y = -vSelection;
			}
			composite.setLocation (location);
		}
	});
	shell.open ();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

}
', now(), now());
insert into SNIPPET values (10290, 1, '[SWT]SnippetSnippet90.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Tree example snippet: detect mouse down in a tree item
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet90 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	final Tree tree = new Tree (shell, SWT.BORDER | SWT.MULTI);
	for (int i=0; i<12; i++) {
		TreeItem treeItem = new TreeItem (tree, SWT.NONE);
		treeItem.setText ("Item " + i);
	}
	tree.addListener (SWT.MouseDown, new Listener () {
		public void handleEvent (Event event) {
			Point point = new Point (event.x, event.y);
			TreeItem item = tree.getItem (point);
			if (item != null) {
				System.out.println ("Mouse down: " + item);
			}
		}
	});
	tree.setSize (200, 200);
	shell.setSize (300, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}

}
', now(), now());
insert into SNIPPET values (10291, 1, '[SWT]SnippetSnippet91.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Drag and Drop example snippet: drag leaf items in a tree
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet91 {

public static void main (String [] args) {
	
	final Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.setLayout(new FillLayout());
	final Tree tree = new Tree(shell, SWT.BORDER);
	for (int i = 0; i < 3; i++) {
		TreeItem item = new TreeItem(tree, SWT.NONE);
		item.setText("item "+i);
		for (int j = 0; j < 3; j++) {
			TreeItem subItem = new TreeItem(item, SWT.NONE);
			subItem.setText("item "+i+" "+j);
			for (int k = 0; k < 3; k++) {
				TreeItem subsubItem = new TreeItem(subItem, SWT.NONE);
				subsubItem.setText("item "+i+" "+j+" "+k);
			}
		}
	}
	
	Transfer[] types = new Transfer[] {TextTransfer.getInstance()};
	int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_LINK;
	
	final DragSource source = new DragSource (tree, operations);
	source.setTransfer(types);
	final TreeItem[] dragSourceItem = new TreeItem[1];
	source.addDragListener (new DragSourceListener () {
		public void dragStart(DragSourceEvent event) {
			TreeItem[] selection = tree.getSelection();
			if (selection.length > 0 && selection[0].getItemCount() == 0) {
				event.doit = true;
				dragSourceItem[0] = selection[0];
			} else {
				event.doit = false;
			}
		};
		public void dragSetData (DragSourceEvent event) {
			event.data = dragSourceItem[0].getText();
		}
		public void dragFinished(DragSourceEvent event) {
			if (event.detail == DND.DROP_MOVE)
				dragSourceItem[0].dispose();
				dragSourceItem[0] = null;
		}
	});

	DropTarget target = new DropTarget(tree, operations);
	target.setTransfer(types);
	target.addDropListener (new DropTargetAdapter() {
		public void dragOver(DropTargetEvent event) {
			event.feedback = DND.FEEDBACK_EXPAND | DND.FEEDBACK_SCROLL;
			if (event.item != null) {
				TreeItem item = (TreeItem)event.item;
				Point pt = display.map(null, tree, event.x, event.y);
				Rectangle bounds = item.getBounds();
				if (pt.y < bounds.y + bounds.height/3) {
					event.feedback |= DND.FEEDBACK_INSERT_BEFORE;
				} else if (pt.y > bounds.y + 2*bounds.height/3) {
					event.feedback |= DND.FEEDBACK_INSERT_AFTER;
				} else {
					event.feedback |= DND.FEEDBACK_SELECT;
				}
			}
		}
		public void drop(DropTargetEvent event) {
			if (event.data == null) {
				event.detail = DND.DROP_NONE;
				return;
			}
			String text = (String)event.data;
			if (event.item == null) {
				TreeItem item = new TreeItem(tree, SWT.NONE);
				item.setText(text);
			} else {
				TreeItem item = (TreeItem)event.item;
				Point pt = display.map(null, tree, event.x, event.y);
				Rectangle bounds = item.getBounds();
				TreeItem parent = item.getParentItem();
				if (parent != null) {
					TreeItem[] items = parent.getItems();
					int index = 0;
					for (int i = 0; i < items.length; i++) {
						if (items[i] == item) {
							index = i;
							break;
						}
					}
					if (pt.y < bounds.y + bounds.height/3) {
						TreeItem newItem = new TreeItem(parent, SWT.NONE, index);
						newItem.setText(text);
					} else if (pt.y > bounds.y + 2*bounds.height/3) {
						TreeItem newItem = new TreeItem(parent, SWT.NONE, index+1);
						newItem.setText(text);
					} else {
						TreeItem newItem = new TreeItem(item, SWT.NONE);
						newItem.setText(text);
					}
					
				} else {
					TreeItem[] items = tree.getItems();
					int index = 0;
					for (int i = 0; i < items.length; i++) {
						if (items[i] == item) {
							index = i;
							break;
						}
					}
					if (pt.y < bounds.y + bounds.height/3) {
						TreeItem newItem = new TreeItem(tree, SWT.NONE, index);
						newItem.setText(text);
					} else if (pt.y > bounds.y + 2*bounds.height/3) {
						TreeItem newItem = new TreeItem(tree, SWT.NONE, index+1);
						newItem.setText(text);
					} else {
						TreeItem newItem = new TreeItem(item, SWT.NONE);
						newItem.setText(text);
					}
				}
				
				
			}
		}
	});

	shell.setSize (400, 400);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10292, 1, '[SWT]SnippetSnippet92.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Cursor example snippet: create a cursor from a source and a mask
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet92 {

public static void main (String [] args) {
	Display display = new Display();
	Color white = display.getSystemColor (SWT.COLOR_WHITE);
	Color black = display.getSystemColor (SWT.COLOR_BLACK);
	
	//Create a source ImageData of depth 1 (monochrome)
	PaletteData palette = new PaletteData (new RGB [] {white.getRGB(), black.getRGB(),});
	ImageData sourceData = new ImageData (20, 20, 1, palette);
	for (int i = 0; i < 10; i ++) {
		for (int j = 0; j < 20; j++) {
			sourceData.setPixel(i, j, 1);
		}
	}
	
	//Create a mask ImageData of depth 1 (monochrome)
	palette = new PaletteData (new RGB [] {white.getRGB(), black.getRGB(),});
	ImageData maskData = new ImageData (20, 20, 1, palette);
	for (int i = 0; i < 20; i ++) {
		for (int j = 0; j < 10; j++) {
			maskData.setPixel(i, j, 1);
		}
	}
	//Create cursor
	Cursor cursor = new Cursor(display, sourceData, maskData, 10, 10);
	
	Shell shell = new Shell(display);
	final Image source = new Image (display,sourceData);
	final Image mask = new Image (display, maskData);
	//Draw source and mask just to show what they look like
	shell.addPaintListener(new PaintListener() {
		public void paintControl(PaintEvent e) {
			GC gc = e.gc;
			gc.drawString("source: ", 10, 10);
			gc.drawImage(source, 0, 0, 20, 20, 60, 10, 20, 20);
			gc.drawString("mask: ",10, 40);
			gc.drawImage(mask, 0, 0, 20, 20, 60, 40, 20, 20);
		}
	});
	shell.setSize(150, 150);
	shell.open();
	shell.setCursor(cursor);
	
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	cursor.dispose();
	source.dispose();
	mask.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10293, 1, '[SWT]SnippetSnippet93.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * GC example snippet: measure a string
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.layout.*;

public class Snippet93 {

public static void main (String [] args) {
	Display display = new Display ();
	Shell shell = new Shell (display);
	shell.setLayout (new RowLayout ());
	Label label = new Label (shell, SWT.NONE);
	GC gc = new GC (label);
	Point size = gc.textExtent ("Hello");
	gc.dispose ();
	label.setText ("Hello -> " + size);
	shell.pack ();
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10294, 1, '[SWT]SnippetSnippet94.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * Clipboard example snippet: copy and paste data with the clipboard
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet94 {

public static void main( String[] args) {
	Display display = new Display ();
	final Clipboard cb = new Clipboard(display);
	final Shell shell = new Shell (display);
	shell.setLayout(new FormLayout());
	final Text text = new Text(shell, SWT.BORDER | SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL);
	
	Button copy = new Button(shell, SWT.PUSH);
	copy.setText("Copy");
	copy.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			String textData = text.getSelectionText();
			if (textData.length() > 0) {
				TextTransfer textTransfer = TextTransfer.getInstance();
				cb.setContents(new Object[]{textData}, new Transfer[]{textTransfer});
			}
		}
	});
	
	Button paste = new Button(shell, SWT.PUSH);
	paste.setText("Paste");
	paste.addListener (SWT.Selection, new Listener () {
		public void handleEvent (Event e) {
			TextTransfer transfer = TextTransfer.getInstance();
			String data = (String)cb.getContents(transfer);
			if (data != null) {
				text.insert(data);
			}
		}
	});
	
	FormData data = new FormData();
	data.left = new FormAttachment(paste, 0, SWT.LEFT);
	data.right = new FormAttachment(100, -5);
	data.top = new FormAttachment(0, 5);
	copy.setLayoutData(data);
	
	data = new FormData();
	data.right = new FormAttachment(100, -5);
	data.top = new FormAttachment(copy, 5);
	paste.setLayoutData(data);
	
	data = new FormData();
	data.left = new FormAttachment(0, 5);
	data.top = new FormAttachment(0, 5);
	data.right = new FormAttachment(paste, -5);
	data.bottom = new FormAttachment(100, -5);
	text.setLayoutData(data);
	
	shell.setSize(200, 200);
	shell.open();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	cb.dispose();
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10295, 1, '[SWT]SnippetSnippet95.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * GC example snippet: capture a widget image with a GC
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.widgets.*;

public class Snippet95 {

public static void main(String[] args) {
	final Display display = new Display();
	final Shell shell = new Shell(display);
	shell.setText("Widget");
	
	final Table table = new Table(shell, SWT.MULTI);
	table.setLinesVisible(true);
	table.setBounds(10, 10, 100, 100);
	for (int i = 0; i < 9; i++) {
		new TableItem(table, SWT.NONE).setText("item" + i);
	}
	
	Button button = new Button(shell, SWT.PUSH);
	button.setText("Capture");
	button.pack();
	button.setLocation(10, 140);
	button.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event event) {
			Point tableSize = table.getSize();
			GC gc = new GC(table);
			final Image image =
				new Image(display, tableSize.x, tableSize.y);
			gc.copyArea(image, 0, 0);
			gc.dispose();
			
			Shell popup = new Shell(shell);
			popup.setText("Image");
			popup.addListener(SWT.Close, new Listener() {
				public void handleEvent(Event e) {
					image.dispose();
				}
			});
			
			Canvas canvas = new Canvas(popup, SWT.NONE);
			canvas.setBounds(10, 10, tableSize.x+10, tableSize.y+10);
			canvas.addPaintListener(new PaintListener() {
				public void paintControl(PaintEvent e) {
					e.gc.drawImage(image, 0, 0);
				}
			});
			popup.pack();
			popup.open();
		}
	});
	shell.pack();
	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch()) display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10296, 1, '[SWT]SnippetSnippet96.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;

/*
 * TableCursor example snippet: navigate a table cells with arrow keys. 
 * Edit when user hits Return key.  Exit edit mode by hitting Escape (cancels edit)
 * or Return (applies edit to table).
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.custom.*;
import org.eclipse.swt.events.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet96 {

public static void main(String[] args) {
	Display display = new Display();
	Shell shell = new Shell(display);
	shell.setLayout(new GridLayout());

	// create a a table with 3 columns and fill with data
	final Table table = new Table(shell, SWT.BORDER | SWT.MULTI | SWT.FULL_SELECTION);
	table.setLayoutData(new GridData(GridData.FILL_BOTH));
	TableColumn column1 = new TableColumn(table, SWT.NONE);
	TableColumn column2 = new TableColumn(table, SWT.NONE);
	TableColumn column3 = new TableColumn(table, SWT.NONE);
	for (int i = 0; i < 100; i++) {
		TableItem item = new TableItem(table, SWT.NONE);
		item.setText(new String[] {"cell " + i + " 0", 	"cell " + i + " 1", "cell " + i + " 2" });
	}
	column1.pack();
	column2.pack();
	column3.pack();

	// create a TableCursor to navigate around the table
	final TableCursor cursor = new TableCursor(table, SWT.NONE);
	// create an editor to edit the cell when the user hits "ENTER" 
	// while over a cell in the table
	final ControlEditor editor = new ControlEditor(cursor);
	editor.grabHorizontal = true;
	editor.grabVertical = true;

	cursor.addSelectionListener(new SelectionAdapter() {
		// when the TableEditor is over a cell, select the corresponding row in 
		// the table
		public void widgetSelected(SelectionEvent e) {
			table.setSelection(new TableItem[] { cursor.getRow()});
		}
		// when the user hits "ENTER" in the TableCursor, pop up a text editor so that 
		// they can change the text of the cell
		public void widgetDefaultSelected(SelectionEvent e) {
			final Text text = new Text(cursor, SWT.NONE);
			TableItem row = cursor.getRow();
			int column = cursor.getColumn();
			text.setText(row.getText(column));
			text.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					// close the text editor and copy the data over 
					// when the user hits "ENTER"
					if (e.character == SWT.CR) {
						TableItem row = cursor.getRow();
						int column = cursor.getColumn();
						row.setText(column, text.getText());
						text.dispose();
					}
					// close the text editor when the user hits "ESC"
					if (e.character == SWT.ESC) {
						text.dispose();
					}
				}
			});
			// close the text editor when the user tabs away
			text.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					text.dispose();
				}
			});
			editor.setEditor(text);
			text.setFocus();
		}
	});
	// Hide the TableCursor when the user hits the "CTRL" or "SHIFT" key.
	// This alows the user to select multiple items in the table.
	cursor.addKeyListener(new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if (e.keyCode == SWT.CTRL
				|| e.keyCode == SWT.SHIFT
				|| (e.stateMask & SWT.CONTROL) != 0
				|| (e.stateMask & SWT.SHIFT) != 0) {
				cursor.setVisible(false);
			}
		}
	});
	// When the user double clicks in the TableCursor, pop up a text editor so that 
	// they can change the text of the cell
	cursor.addMouseListener(new MouseAdapter() {
		public void mouseDown(MouseEvent e) {
			final Text text = new Text(cursor, SWT.NONE);
			TableItem row = cursor.getRow();
			int column = cursor.getColumn();
			text.setText(row.getText(column));
			text.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					// close the text editor and copy the data over 
					// when the user hits "ENTER"
					if (e.character == SWT.CR) {
						TableItem row = cursor.getRow();
						int column = cursor.getColumn();
						row.setText(column, text.getText());
						text.dispose();
					}
					// close the text editor when the user hits "ESC"
					if (e.character == SWT.ESC) {
						text.dispose();
					}
				}
			});
			// close the text editor when the user clicks away
			text.addFocusListener(new FocusAdapter() {
				public void focusLost(FocusEvent e) {
					text.dispose();
				}
			});
			editor.setEditor(text);
			text.setFocus();
		}
	});
	
	// Show the TableCursor when the user releases the "SHIFT" or "CTRL" key.
	// This signals the end of the multiple selection task.
	table.addKeyListener(new KeyAdapter() {
		public void keyReleased(KeyEvent e) {
			if (e.keyCode == SWT.CONTROL && (e.stateMask & SWT.SHIFT) != 0)
				return;
			if (e.keyCode == SWT.SHIFT && (e.stateMask & SWT.CONTROL) != 0)
				return;
			if (e.keyCode != SWT.CONTROL
				&& (e.stateMask & SWT.CONTROL) != 0)
				return;
			if (e.keyCode != SWT.SHIFT && (e.stateMask & SWT.SHIFT) != 0)
				return;

			TableItem[] selection = table.getSelection();
			TableItem row = (selection.length == 0) ? table.getItem(table.getTopIndex()) : selection[0];
			table.showItem(row);
			cursor.setSelection(row, 0);
			cursor.setVisible(true);
			cursor.setFocus();
		}
	});

	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10297, 1, '[SWT]SnippetSnippet97.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Menu example snippet: fill a menu dynamically (when menu shown)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet97 {

public static void main (String [] args) {
	final Display display = new Display ();
	Shell shell = new Shell (display);
	final Tree tree = new Tree (shell, SWT.BORDER | SWT.MULTI);
	final Menu menu = new Menu (shell, SWT.POP_UP);
	tree.setMenu (menu);
	for (int i=0; i<12; i++) {
		TreeItem item = new TreeItem (tree, SWT.NONE);
		item.setText ("Item " + i);
	}
	menu.addListener (SWT.Show, new Listener () {
		public void handleEvent (Event event) {
			MenuItem [] menuItems = menu.getItems ();
			for (int i=0; i<menuItems.length; i++) {
				menuItems [i].dispose ();
			}
			TreeItem [] treeItems = tree.getSelection ();
			for (int i=0; i<treeItems.length; i++) {
				MenuItem menuItem = new MenuItem (menu, SWT.PUSH);
				menuItem.setText (treeItems [i].getText ());
			}
		}
	});
	tree.setSize (200, 200);
	shell.setSize (300, 300);
	shell.open ();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
} 
', now(), now());
insert into SNIPPET values (10298, 1, '[SWT]SnippetSnippet98.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Composite example snippet: create and dispose children of a composite
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

public class Snippet98 {

static int pageNum = 0;
static Composite pageComposite;

public static void main(String args[]) {
	Display display = new Display();
	final Shell shell = new Shell(display);
	shell.setLayout(new GridLayout());
	Button button = new Button(shell, SWT.PUSH);
	button.setText("Push");
	pageComposite = new Composite(shell, SWT.NONE);
	pageComposite.setLayout(new GridLayout());
	pageComposite.setLayoutData(new GridData());

	button.addListener(SWT.Selection, new Listener() {
		public void handleEvent(Event event) {
			if ((pageComposite != null) && (!pageComposite.isDisposed())) {
				pageComposite.dispose();
			}
			pageComposite = new Composite(shell, SWT.NONE);
			pageComposite.setLayout(new GridLayout());
			pageComposite.setLayoutData(new GridData());
			if (pageNum++ % 2 == 0) {
				Table table = new Table(pageComposite, SWT.BORDER);
				table.setLayoutData(new GridData());
				for (int i = 0; i < 5; i++) {
					new TableItem(table, SWT.NONE).setText("table item " + i);
				}
			} else {
				new Button(pageComposite, SWT.RADIO).setText("radio");
			}
			shell.layout(true);
		}
	});

	shell.open();
	while (!shell.isDisposed()) {
		if (!display.readAndDispatch())
			display.sleep();
	}
	display.dispose();
}
}
', now(), now());
insert into SNIPPET values (10299, 1, '[SWT]SnippetSnippet99.java', '/*******************************************************************************
 * Copyright (c) 2000, 2004 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.snippets;
 
/*
 * Shell example snippet: prevent a shell from closing (prompt the user)
 *
 * For a list of all SWT example snippets see
 * http://www.eclipse.org/swt/snippets/
 */
import org.eclipse.swt.*;
import org.eclipse.swt.widgets.*;

public class Snippet99 {

public static void main (String [] args) {
	Display display = new Display ();
	final Shell shell = new Shell (display);
	shell.addListener (SWT.Close, new Listener () {
		public void handleEvent (Event event) {
			int style = SWT.APPLICATION_MODAL | SWT.YES | SWT.NO;
			MessageBox messageBox = new MessageBox (shell, style);
			messageBox.setText ("Information");
			messageBox.setMessage ("Close the shell?");
			event.doit = messageBox.open () == SWT.YES;
		}
	});
	shell.pack ();
	shell.open();
	while (!shell.isDisposed ()) {
		if (!display.readAndDispatch ()) display.sleep ();
	}
	display.dispose ();
}
}
', now(), now());
insert into SNIPPET values (10300, 1, '[SWT]SnippetSnippetLauncher.java', 'package org.eclipse.swt.snippets;

/*
 * Simple "hackable" code that runs all of the SWT Snippets,
 * typically for testing. One example of a useful "hack" is
 * to add the line:
 *    if (source.indexOf("Table") == -1 && source.indexOf("Tree") == -1) continue;
 * after the line:
 *    String source = String.valueOf(buffer);
 * in order to run all of the Table and Tree Snippets.
 */
import java.lang.reflect.*;
import java.io.*;
import org.eclipse.swt.SWT;

public class SnippetLauncher {

	public static void main (String [] args) {
		File sourceDir = new File("src/org/eclipse/swt/snippets");
		boolean hasSource = sourceDir.exists();
		int count = 300;
		if (hasSource) {
			File [] files = sourceDir.listFiles();
			if (files.length > 0) count = files.length;
		}
		for (int i = 1; i < count; i++) {
			if (i == 136 || i == 151 || i == 180 || i == 219) continue;
			String className = "Snippet" + i;
			Class clazz = null;
			try {
				clazz = Class.forName("org.eclipse.swt.snippets." + className);
			} catch (ClassNotFoundException e) {}
			if (clazz != null) {
				System.out.println("\n" + clazz.getName());
				if (hasSource) {
					File sourceFile = new File(sourceDir, className + ".java");
					try {
						FileReader reader = new FileReader(sourceFile);
						char [] buffer = new char [(int)sourceFile.length()];
						reader.read(buffer);
						String source = String.valueOf(buffer);
						int start = source.indexOf("package");
						start = source.indexOf("/*", start);
						int end = source.indexOf("* For a list of all");
						System.out.println(source.substring(start, end-3));
						boolean skip = false;
						String platform = SWT.getPlatform();
						if (source.indexOf("PocketPC") != -1) {
							platform = "PocketPC";
							skip = true;
						} else if (source.indexOf("OpenGL") != -1) {
							platform = "OpenGL";
							skip = true;
						} else if (source.indexOf("JavaXPCOM") != -1) {
							platform = "JavaXPCOM";
							skip = true;
						} else {
							String [] platforms = {"win32", "motif", "gtk", "photon", "carbon"};
							for (int p = 0; p < platforms.length; p++) {
								if (!platforms[p].equals(platform) && source.indexOf("." + platforms[p]) != -1) {
									platform = platforms[p];
									skip = true;
									break;
								}
							}
						}
						if (skip) {
							System.out.println("...skipping " + platform + " example...");
							continue;
						}
					} catch (Exception e) {}
				}
				Method method = null;
				String [] param = new String [0];
				if (i == 81) param = new String[] {"Shell.Explorer"};
				try {
					method = clazz.getMethod("main", new Class[] {param.getClass()});
				} catch (NoSuchMethodException e) {
					System.out.println("   Did not find main(String [])");
				}
				if (method != null) {
					try {
						method.invoke(clazz, new Object [] {param});
					} catch (IllegalAccessException e) {
						System.out.println("   Failed to launch (illegal access)");
					} catch (IllegalArgumentException e) {
						System.out.println("   Failed to launch (illegal argument to main)");
					} catch (InvocationTargetException e) {
						System.out.println("   Exception in Snippet: " + e.getTargetException());
					}
				}
			}
		}
	}
}
', now(), now());
