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

package com.plugin.arview.augmentedreality.appunta.android.point.impl;

import android.graphics.Bitmap;
import android.location.Location;

import com.playground.skypass.model.ModelMonster;
import com.plugin.arview.augmentedreality.appunta.android.point.Point;
import com.plugin.arview.augmentedreality.appunta.android.point.renderer.PointRenderer;
import com.playground.skypass.app.model.MarchantData;

/***
 * A single point representing a place, it contains information on where it's
 * located in space, in screen, it's id and name and the name of the renderer to
 * use to draw it.
 * 

 *
 */
public class SimplePoint implements Point {

	public static int MILLION = 1000000;

	private Location location;
	private double distance;
	private String name;
	private PointRenderer renderer;
	private float x;
	private float y;
	private boolean selected;
	private String id;
	private String distance_from_client;
	private String address;
	private ModelMonster.DataBean data;
	private int xp;
	private int point;
	private double geofence;
	private String title;
	private String desc;
	private Bitmap image;
	private String imgUrl;
	private int type;

	@Override
	public String getDistanceFromClient() {
		return distance_from_client;
	}

	@Override
	public void setDistanceFromClient(String distance_from_client) {

	}

	private boolean drawn=true;
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public SimplePoint(String id, Location location, PointRenderer renderer, String title, String desc,
					   int xp, int point, ModelMonster.DataBean data, Bitmap image, double geofence, String imageUrl,
					   int type) {
		super();
		this.id = id;
		this.setLocation(location);
		this.renderer=renderer;
		this.title = title;
		this.desc = desc;
		this.xp = xp;
		this.point = point;
		this.data = data;
		this.image = image;
		this.geofence = geofence;
		this.imgUrl = imageUrl;
		this.type = type;
	}
//	public SimplePoint(int id, Location location, PointRenderer renderer) {
//		this(id,location,renderer,"");
//	}
//
//	public SimplePoint(int id, Location location) {
//		this(id,location,null);
//	}

	
	/* (non-Javadoc)
	 * @see Point#getDistance()
	 */
	@Override
	public double getDistance() {
		return distance;
	}
	/* (non-Javadoc)
	 * @see Point#setDistance(double)
	 */
	@Override
	public void setDistance(double distance) {
		this.distance = distance;
	}
	/* (non-Javadoc)
	 * @see Point#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	/* (non-Javadoc)
	 * @see Point#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
	/* (non-Javadoc)
	 * @see Point#getRenderer()
	 */
	@Override
	public PointRenderer getRenderer() {
		return renderer;
	}
	/* (non-Javadoc)
	 * @see Point#setRenderer(PointRenderer)
	 */
	@Override
	public void setRenderer(PointRenderer renderer) {
		this.renderer = renderer;
	}

	/* (non-Javadoc)
	 * @see Point#getX()
	 */
	@Override
	public float getX() {
		return x;
	}

	/* (non-Javadoc)
	 * @see Point#setX(float)
	 */
	@Override
	public void setX(float x) {
		this.x = x;
	}
	/* (non-Javadoc)
	 * @see Point#getY()
	 */
	@Override
	public float getY() {
		return y;
	}

	/* (non-Javadoc)
	 * @see Point#setY(float)
	 */
	@Override
	public void setY(float y) {
		this.y = y;
	}
	/* (non-Javadoc)
	 * @see Point#getLocation()
	 */
	@Override
	public Location getLocation() {
		return location;
	}
	/* (non-Javadoc)
	 * @see Point#setLocation(android.location.Location)
	 */
	@Override
	public void setLocation(Location location) {
		this.location = location;
	}
	@Override
	public boolean isDrawn() {
		return drawn;
	}
	@Override
	public void setDrawn(boolean drawn) {
		this.drawn=drawn;
	}

	@Override
	public ModelMonster.DataBean getData() {
		return data;
	}

	@Override
	public void setData(MarchantData data) {

	}

	@Override
	public int getXp() {
		return xp;
	}

	@Override
	public int getPoint() {
		return point;
	}

	@Override
	public double getGeofence() {
		return this.geofence;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String getDesc() {
		return desc;
	}

	@Override
	public String getImageUrl() {
		return this.imgUrl;
	}

	@Override
	public Bitmap getImage() {
		return image;
	}

	@Override
	public int getType() {
		return type;
	}
}
