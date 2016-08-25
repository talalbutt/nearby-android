package com.esri.android.nearbyplaces.route;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.esri.android.nearbyplaces.R;
import com.esri.android.nearbyplaces.filter.FilterContract;
import com.esri.arcgisruntime.tasks.route.DirectionManeuver;
import com.esri.arcgisruntime.tasks.route.DirectionManeuverType;

import java.util.List;

/**
 * Created by sand8529 on 8/25/16.
 */
public class RouteDirectionsFragment extends DialogFragment {

  private List<DirectionManeuver> mDirectionManeuvers;
  private final static String TAG = RouteDirectionsFragment.class.getSimpleName();

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.route_direction_list, container,false);

    // Setup list adapter
    ListView listView = (ListView) view.findViewById(R.id.directions_list);
    listView.setAdapter(new DirectionsListAdapter(mDirectionManeuvers));
    return view;
  }

  public void setRoutingDirections(List<DirectionManeuver> directions){
    mDirectionManeuvers = directions;
  }
  /**
   * List adapter for the list of route directions.
   */
  private class DirectionsListAdapter extends ArrayAdapter<DirectionManeuver> {
    public DirectionsListAdapter(List<DirectionManeuver> directions) {
      super(getActivity(), 0, directions);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      // Inflate view if we haven't been given one to reuse
      View v = convertView;
      if (convertView == null) {
        v = getActivity().getLayoutInflater().inflate(R.layout.route_direction_list_item, parent, false);
      }

      // Configure the view for this item
      DirectionManeuver direction = getItem(position);
      ImageView imageView = (ImageView) v.findViewById(R.id.directions_maneuver_imageview);
      Drawable drawable = getRoutingIcon(direction.getManeuverType());
      if (drawable != null) {
        imageView.setImageDrawable(drawable);
      }
      TextView textView = (TextView) v.findViewById(R.id.directions_text_textview);
      textView.setText(direction.getDirectionText());
      textView = (TextView) v.findViewById(R.id.directions_length_textview);
      String lengthString = String.format("%.1f mi", direction.getLength());
      textView.setText(lengthString);
      return v;
    }

    private Drawable getRoutingIcon(DirectionManeuverType maneuver) {
      Context context = getActivity();
      int id;
      switch (maneuver) {
        case STRAIGHT :
          id = R.drawable.ic_routing_straight_arrow;
          break;
        case BEAR_LEFT :
          id = R.drawable.ic_routing_bear_left;
          break;
        case BEAR_RIGHT :
          id = R.drawable.ic_routing_bear_right;
          break;
        case TURN_LEFT :
          id = R.drawable.ic_routing_turn_left;
          break;
        case TURN_RIGHT :
          id = R.drawable.ic_routing_turn_right;
          break;
        case SHARP_LEFT :
          id = R.drawable.ic_routing_turn_sharp_left;
          break;
        case SHARP_RIGHT :
          id = R.drawable.ic_routing_turn_sharp_right;
          break;
        case U_TURN :
          id = R.drawable.ic_routing_u_turn;
          break;
        case FERRY :
          id = R.drawable.ic_routing_take_ferry;
          break;
        case ROUNDABOUT :
          id = R.drawable.ic_routing_get_on_roundabout;
          break;
        case HIGHWAY_MERGE :
          id = R.drawable.ic_routing_merge_onto_highway;
          break;
        case HIGHWAY_CHANGE :
          id = R.drawable.ic_routing_highway_change;
          break;
        case FORK_CENTER :
          id = R.drawable.ic_routing_take_center_fork;
          break;
        case FORK_LEFT :
          id = R.drawable.ic_routing_take_fork_left;
          break;
        case FORK_RIGHT :
          id = R.drawable.ic_routing_take_fork_right;
          break;
        case END_OF_FERRY :
          id = R.drawable.ic_routing_get_off_ferry;
          break;
        case RAMP_RIGHT :
          id = R.drawable.ic_routing_take_ramp_right;
          break;
        case RAMP_LEFT :
          id = R.drawable.ic_routing_take_ramp_left;
          break;
        case TURN_LEFT_RIGHT :
          id = R.drawable.ic_routing_left_right;
          break;
        case TURN_RIGHT_LEFT :
          id = R.drawable.ic_routing_right_left;
          break;
        case TURN_RIGHT_RIGHT :
          id = R.drawable.ic_routing_right_right;
          break;
        case TURN_LEFT_LEFT :
          id = R.drawable.ic_routing_left_left;
          break;
        case STOP :
          id = R.drawable.route_pin_end;
          break;
        case HIGHWAY_EXIT :
        case DEPART :
        case TRIP_ITEM :
        case PEDESTRIAN_RAMP :
        case ELEVATOR :
        case ESCALATOR :
        case STAIRS :
        case DOOR_PASSAGE :
        default :
          Log.w(TAG, maneuver.name() + "not supported");
          return null;
      }
      try {
        return context.getResources().getDrawable(id);
      } catch (Resources.NotFoundException e) {
        Log.w(TAG, "No drawable found for" + maneuver.name());
        return null;
      }
    }

  }
}