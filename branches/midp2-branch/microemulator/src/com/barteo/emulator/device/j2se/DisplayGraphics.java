/*
 *  MicroEmulator
 *  Copyright (C) 2001 Bartek Teodorczyk <barteo@it.pl>
 *
 *  This library is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public
 *  License as published by the Free Software Foundation; either
 *  version 2.1 of the License, or (at your option) any later version.
 *
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this library; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 *  Contributor(s):
 *    3GLab
 */
 
package com.barteo.emulator.device.j2se;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

import com.barteo.emulator.device.DeviceDisplay;
import com.barteo.emulator.device.DeviceFactory;


public class DisplayGraphics extends javax.microedition.lcdui.Graphics 
{
  java.awt.Graphics2D g;
  int color = 0;
  javax.microedition.lcdui.Font currentFont = javax.microedition.lcdui.Font.getDefaultFont();
  

// to zostanie zmienione na protected jak zostanie zrobiony DisplayBridge
  public DisplayGraphics(java.awt.Graphics2D a_g) 
  {
    g = a_g;
    g.setFont(
        ((J2SEFontManager) DeviceFactory.getDevice().getFontManager()).getFontMetrics(currentFont).getFont());
  }


  public int getColor()
  {
    return color;
  }

  
  public void setColor(int RGB) 
  {
		java.awt.image.RGBImageFilter filter = null;
    color = RGB;
    
    DeviceDisplay deviceDisplay = DeviceFactory.getDevice().getDeviceDisplay();
    if (deviceDisplay.isColor()) {
			filter = new RGBImageFilter();
    } else {
      if (deviceDisplay.numColors() == 2) {
        filter = new BWImageFilter();
      } else {
        filter = new GrayImageFilter();
      }
    }

    g.setColor(new Color(filter.filterRGB(0, 0, RGB)));
  }


	public javax.microedition.lcdui.Font getFont()
	{
		return currentFont;
	}


	public void setFont(javax.microedition.lcdui.Font font)
	{
		currentFont = font;
    g.setFont(
        ((J2SEFontManager) DeviceFactory.getDevice().getFontManager()).getFontMetrics(currentFont).getFont());
	}


  public void clipRect(int x, int y, int width, int height) 
  {
    g.clipRect(x, y, width, height);
  }


  public void setClip(int x, int y, int width, int height) 
  {
    g.setClip(x, y, width, height);
  }


  public int getClipX()
  {
    Rectangle rect = g.getClipBounds();
    if (rect == null) {
      return 0;
    } else {
      return rect.x;
    }
  }


  public int getClipY()
  {
    Rectangle rect = g.getClipBounds();
    if (rect == null) {
      return 0;
    } else {
      return rect.y;
    }
  }


  public int getClipHeight()
  {
    Rectangle rect = g.getClipBounds();
    if (rect == null) {
      return DeviceFactory.getDevice().getDeviceDisplay().getHeight();
    } else {
      return rect.height;
    }
  }


  public int getClipWidth()
  {
    Rectangle rect = g.getClipBounds();
    if (rect == null) {
      return DeviceFactory.getDevice().getDeviceDisplay().getWidth();
    } else {
      return rect.width;
    }
  }


  public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) 
  {
    g.drawArc(x, y, width, height, startAngle, arcAngle);
  }


  public void drawImage(Image img, int x, int y, int anchor) 
  {
    int newx = x;
    int newy = y;

    if (anchor == 0) {
      anchor = javax.microedition.lcdui.Graphics.TOP | javax.microedition.lcdui.Graphics.LEFT;
    }

    if ((anchor & javax.microedition.lcdui.Graphics.RIGHT) != 0) {
      newx -= img.getWidth();
    } else if ((anchor & javax.microedition.lcdui.Graphics.HCENTER) != 0) {
      newx -= img.getWidth() / 2;
    }
    if ((anchor & javax.microedition.lcdui.Graphics.BOTTOM) != 0) {
      newy -= img.getHeight();
    } else if ((anchor & javax.microedition.lcdui.Graphics.VCENTER) != 0) {
      newy -= img.getHeight() / 2;
    }

    if (img.isMutable()) {
      g.drawImage(((MutableImage) img).getImage(), newx, newy, null);
    } else {
      g.drawImage(((ImmutableImage) img).getImage(), newx, newy, null);
    }
  }


  public void drawLine(int x1, int y1, int x2, int y2) 
  {
    g.drawLine(x1, y1, x2, y2);
  }


  public void drawRect(int x, int y, int width, int height) 
  {
    drawLine(x, y, x + width, y);
    drawLine(x + width, y, x + width, y + height);
    drawLine(x + width, y + height, x, y + height);
    drawLine(x, y + height, x, y);
  }


	public void drawRegion(Image src, int x_src, int y_src, int width, int height,
			int transform, int x_dest, int y_dest, int anchor)
	{
//		throw new RuntimeException("TODO");
		java.awt.Image img;
		if (src.isMutable()) {
			img = ((MutableImage) src).getImage();
		} else {
			img = ((ImmutableImage) src).getImage();
		}
		if (transform == Sprite.TRANS_NONE) {
			g.drawImage(img,
					x_dest, y_dest, x_dest + width, y_dest + height,
					x_src, y_src, x_src + width, y_src + height, null);
		} else {
			BufferedImage bi = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_ARGB);
			java.awt.Graphics big = bi.getGraphics();
			big.drawImage(img, 0, 0, null);
			
			bi = bi.getSubimage(x_src, y_src, width, height); 
			
			AffineTransform af = new AffineTransform();
			switch (transform) {
				case Sprite.TRANS_MIRROR:
					af.setTransform(-1, 0, 0, 1, width, 0);
					break;

				case Sprite.TRANS_MIRROR_ROT90:
					af.setTransform(1, 0, 0, -1, 0, height);
				case Sprite.TRANS_ROT90:
					af.rotate(90 * Math.PI/180, width / 2, height / 2);
					break;

				case Sprite.TRANS_MIRROR_ROT180:
					af.setTransform(-1, 0, 0, 1, width, 0);
				case Sprite.TRANS_ROT180:
					af.rotate(180 * Math.PI/180, width / 2, height / 2);
					break;

				case Sprite.TRANS_MIRROR_ROT270:
					af.setTransform(1, 0, 0, -1, 0, height);
				case Sprite.TRANS_ROT270:
					af.rotate(270 * Math.PI/180, width / 2, height / 2);
					break;
			}

			g.translate(x_dest, y_dest);
			g.drawImage(bi, af, null);
			g.translate(-x_dest, -y_dest);
		}
	}


	public void drawRGB(int[] rgbData, int offset, int scanlength, 
			int x, int y, int width, int height, boolean processAlpha)
	{
		BufferedImage img;
		
		if (processAlpha) {
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		} else {
			img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		}
		
		img.setRGB(0, 0, width, height, rgbData, offset, scanlength);
		g.drawImage(img, x, y, null);
	}
	
		
  public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) 
  {
    g.drawRoundRect(x, y, width, height, arcWidth, arcHeight);
  }


  public void drawString(String str, int x, int y, int anchor) 
  {
    int newx = x;
    int newy = y;

    if (anchor == 0) {
      anchor = javax.microedition.lcdui.Graphics.TOP | javax.microedition.lcdui.Graphics.LEFT;
    }

    if ((anchor & javax.microedition.lcdui.Graphics.TOP) != 0) {
      newy += g.getFontMetrics().getAscent();
    } else if ((anchor & javax.microedition.lcdui.Graphics.BOTTOM) != 0) {
      newy -= g.getFontMetrics().getDescent();
    }
    if ((anchor & javax.microedition.lcdui.Graphics.HCENTER) != 0) {
      newx -= g.getFontMetrics().stringWidth(str) / 2;
    } else if ((anchor & javax.microedition.lcdui.Graphics.RIGHT) != 0) {
      newx -= g.getFontMetrics().stringWidth(str);
    }

    g.drawString(str, newx, newy);
    
    if ((currentFont.getStyle() & javax.microedition.lcdui.Font.STYLE_UNDERLINED) != 0) {
      g.drawLine(newx, newy + 1, newx + g.getFontMetrics().stringWidth(str), newy + 1);
    }
  }


  public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) 
  {
    g.fillArc(x, y, width, height, startAngle, arcAngle);
  }


  public void fillRect(int x, int y, int width, int height) 
  {
    g.fillRect(x, y, width, height);
  }


  public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) 
  {
    g.fillRoundRect(x, y, width, height, arcWidth, arcHeight);
  }


  public void translate(int x, int y) 
  {
    super.translate(x, y);
    g.translate(x, y);
  }

}
