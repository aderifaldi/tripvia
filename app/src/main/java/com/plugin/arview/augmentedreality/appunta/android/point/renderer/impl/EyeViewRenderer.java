/*


   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

 */

package com.plugin.arview.augmentedreality.appunta.android.point.renderer.impl;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import com.plugin.arview.augmentedreality.appunta.android.orientation.Orientation;
import com.plugin.arview.augmentedreality.appunta.android.point.Point;
import com.plugin.arview.augmentedreality.appunta.android.point.renderer.PointRenderer;
import com.playground.skypass.R;
import com.radyalabs.irfan.util.AppUtility;

import java.text.DecimalFormatSymbols;

/***
 * This class is used to generate a PointRenderer using a drawable
 * resource

 *
 */
public class EyeViewRenderer implements PointRenderer {
	private Bitmap selectedBitmap=null;
	private Resources res;
	private int xSelectedOff;
	private int ySelectedOff;
	private Paint pText;
	private Paint pBlackLine;
	private Context activity;
	private DecimalFormatSymbols symbols;
	/***
	 * Creates and object able to draw a drawable resource in a Canvas
	 * @param res A resources object in order to retrieve the drawable
	 */
//	public EyeViewRenderer(Resources res, int selectedId, int unselectedId, Context activity) {
//		this.selectedId=selectedId;
//		this.unselectedId=unselectedId;
//		this.res=res;
//		this.activity = activity;
//	}

	public EyeViewRenderer(Resources res, Context activity, Bitmap bitmap) {
		this.res=res;
		this.activity = activity;

		symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
	}
	/***
	 * This methods paints the drawable received in constructor and writes the point name beside it
	 */
	@Override
	public void drawPoint(Point point, Canvas canvas, Orientation orientation) {
		if (selectedBitmap==null) {
			
			//Initialize drawing objects

			selectedBitmap = BitmapFactory.decodeResource(res, R.mipmap.ar_view);
			//imgBitmap = BitmapFactory.decodeResource(res, R.drawable.circle_selected);

			xSelectedOff = selectedBitmap.getWidth()/2;
			ySelectedOff = selectedBitmap.getHeight()/2;
			
			pText = new Paint(Paint.ANTI_ALIAS_FLAG);
			pText.setStyle(Paint.Style.STROKE);
			pText.setTextAlign(Paint.Align.LEFT);
			pText.setTextSize(AppUtility.dpToPx(12, activity));
			pText.setTypeface(Typeface.SANS_SERIF);
			pText.setColor(Color.parseColor("#00A3E4"));
			
			pBlackLine=new Paint(Paint.ANTI_ALIAS_FLAG);
			pBlackLine.setColor(Color.parseColor("#000000"));
			pBlackLine.setTextSize(AppUtility.dpToPx(8, activity));
			pBlackLine.setTypeface(Typeface.SANS_SERIF);
			pBlackLine.setTextAlign(Paint.Align.LEFT);

		}

		canvas.drawBitmap(point.getImage(), point.getX()-xSelectedOff, point.getY()- ySelectedOff, null);

		//canvas.rotate(315, point.getX(), point.getY());
//		canvas.drawText("" + point.getTitle(), point.getX() + AppUtility.dpToPx(50, activity),
//				point.getY() - AppUtility.dpToPx(17, activity), pBlackLine);
		//canvas.drawText(point.getName(), point.getX(), point.getY(), pText);
		//canvas.rotate(-315, point.getX(), point.getY());
	}


}
