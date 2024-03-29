/* 
 *	Copyright (c) 2012 Ronny R�hricht
 *
 *	This file is part of Moloko.
 *
 *	Moloko is free software: you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *
 *	Moloko is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License
 *	along with Moloko.  If not, see <http://www.gnu.org/licenses/>.
 *
 *	Contributors:
 * Ronny R�hricht - implementation
 */

package dev.drsoran.moloko.fragments.base.impl;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import dev.drsoran.moloko.AnnotatedConfigurationSupport;
import dev.drsoran.moloko.IOnSettingsChangedListener;
import dev.drsoran.moloko.MolokoApp;


public class ConfigurableFragmentImpl
{
   private final AnnotatedConfigurationSupport annotatedConfigSupport = new AnnotatedConfigurationSupport();
   
   private final Fragment fragment;
   
   private Activity activity;
   
   private final int settingsMask;
   
   private boolean listenersRegistered;
   
   
   
   public ConfigurableFragmentImpl( Fragment fragment, int settingsMask )
   {
      this.fragment = fragment;
      this.settingsMask = settingsMask;
   }
   
   
   
   public Fragment getFragment()
   {
      return fragment;
   }
   
   
   
   public void onAttach( Activity activity )
   {
      this.activity = activity;
      this.annotatedConfigSupport.onAttach( this.activity );
   }
   
   
   
   public void onCreate( Bundle savedInstanceState )
   {
      if ( savedInstanceState == null )
         configure( fragment.getArguments() );
      else
         configure( savedInstanceState );
   }
   
   
   
   public void onStart()
   {
      if ( !listenersRegistered )
      {
         if ( settingsMask != 0
            && fragment instanceof IOnSettingsChangedListener )
         {
            MolokoApp.getNotifierContext( this.activity )
                     .registerOnSettingsChangedListener( settingsMask,
                                                         (IOnSettingsChangedListener) fragment );
            
            listenersRegistered = true;
         }
      }
   }
   
   
   
   public void onDetach()
   {
      if ( listenersRegistered )
      {
         MolokoApp.getNotifierContext( activity )
                  .unregisterOnSettingsChangedListener( (IOnSettingsChangedListener) fragment );
         
         listenersRegistered = false;
      }
      
      annotatedConfigSupport.onDetach();
   }
   
   
   
   public void setArguments( Bundle args )
   {
      configure( args );
   }
   
   
   
   public Bundle getConfiguration()
   {
      return annotatedConfigSupport.getInstanceStates();
   }
   
   
   
   public void configure( Bundle config )
   {
      annotatedConfigSupport.setInstanceStates( config );
   }
   
   
   
   public void setDefaultConfiguration()
   {
      annotatedConfigSupport.setDefaultInstanceStates();
   }
   
   
   
   public Bundle getDefaultConfiguration()
   {
      return annotatedConfigSupport.getDefaultInstanceStates();
   }
   
   
   
   public < T > Bundle getDefaultConfiguration( T instance, Class< T > clazz )
   {
      return annotatedConfigSupport.getDefaultInstanceState( instance );
   }
   
   
   
   public < T > void registerAnnotatedConfiguredInstance( T instance,
                                                          Class< T > clazz )
   {
      annotatedConfigSupport.registerInstance( instance, clazz );
   }
   
   
   
   public void onSaveInstanceState( Bundle outState )
   {
      annotatedConfigSupport.onSaveInstanceStates( outState );
   }
   
   
   
   public ViewGroup getContentView()
   {
      final View root = fragment.getView();
      
      if ( root != null )
         return (ViewGroup) root.findViewById( android.R.id.content );
      else
         return null;
   }
}
