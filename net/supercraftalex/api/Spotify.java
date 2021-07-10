package net.supercraftalex.api;

import com.spotify.android.appremote.api.ConnectApi;
import com.spotify.android.appremote.api.ContentApi;
import com.spotify.android.appremote.api.ImagesApi;
import com.spotify.android.appremote.api.PlayerApi;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.android.appremote.api.UserApi;
import com.spotify.android.appremote.api.PlayerApi.StreamType;
import com.spotify.protocol.client.CallResult;
import com.spotify.protocol.client.RemoteClient;
import com.spotify.protocol.client.RemoteClientConnector;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.client.RemoteClientConnector.ConnectionCallback;
import com.spotify.protocol.types.Capabilities;
import com.spotify.protocol.types.CrossfadeState;
import com.spotify.protocol.types.Empty;
import com.spotify.protocol.types.ImageUri;
import com.spotify.protocol.types.LibraryState;
import com.spotify.protocol.types.ListItem;
import com.spotify.protocol.types.ListItems;
import com.spotify.protocol.types.PlayerContext;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.UserStatus;
import com.spotify.protocol.types.VolumeState;
import com.spotify.protocol.types.Image.Dimension;
import com.spotify.protocol.types.PlaybackSpeed.PodcastPlaybackSpeed;

public class Spotify {
	
	
	
}
/*

new RemoteClient() {
			@Override
			public <T> void unsubscribe(Subscription<T> arg0) {
				
				
			}
			
			@Override
			public <T> Subscription<T> subscribe(String arg0, Class<T> arg1) {
				
				return null;
			}
			
			@Override
			public <T> CallResult<T> hello(Class<T> arg0) {
				
				return null;
			}
			
			@Override
			public void goodbye() {
				
				
			}
			
			@Override
			public <T> CallResult<T> call(String arg0, Object arg1, Class<T> arg2) {
				
				return null;
			}
			
			@Override
			public <T> CallResult<T> call(String arg0, Class<T> arg1) {
				
				return null;
			}
		}, new PlayerApi() {
			
			@Override
			public CallResult<Empty> toggleShuffle() {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> toggleRepeat() {
				
				return null;
			}
			
			@Override
			public Subscription<PlayerState> subscribeToPlayerState() {
				
				return null;
			}
			
			@Override
			public Subscription<PlayerContext> subscribeToPlayerContext() {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> skipToIndex(String arg0, int arg1) {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> skipPrevious() {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> skipNext() {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> setShuffle(boolean arg0) {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> setRepeat(int arg0) {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> setPodcastPlaybackSpeed(PodcastPlaybackSpeed arg0) {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> seekToRelativePosition(long arg0) {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> seekTo(long arg0) {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> resume() {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> queue(String arg0) {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> play(String arg0, StreamType arg1) {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> play(String arg0) {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> pause() {
				
				return null;
			}
			
			@Override
			public CallResult<PlayerState> getPlayerState() {
				
				return null;
			}
			
			@Override
			public CallResult<CrossfadeState> getCrossfadeState() {
				
				return null;
			}
		}, null, new UserApi() {
			
			@Override
			public Subscription<UserStatus> subscribeToUserStatus() {
				
				return null;
			}
			
			@Override
			public Subscription<Capabilities> subscribeToCapabilities() {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> removeFromLibrary(String arg0) {
				
				return null;
			}
			
			@Override
			public CallResult<LibraryState> getLibraryState(String arg0) {
				
				return null;
			}
			
			@Override
			public CallResult<Capabilities> getCapabilities() {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> addToLibrary(String arg0) {
				
				return null;
			}
		}, new ContentApi() {
			
			@Override
			public CallResult<Empty> playContentItem(ListItem arg0) {
				
				return null;
			}
			
			@Override
			public CallResult<ListItems> getRecommendedContentItems(String arg0) {
				
				return null;
			}
			
			@Override
			public CallResult<ListItems> getChildrenOfItem(ListItem arg0, int arg1, int arg2) {
				
				return null;
			}
		}, new ConnectApi() {
			
			@Override
			public Subscription<VolumeState> subscribeToVolumeState() {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> connectSwitchToLocalDevice() {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> connectSetVolume(float arg0) {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> connectIncreaseVolume() {
				
				return null;
			}
			
			@Override
			public CallResult<Empty> connectDecreaseVolume() {
				
				return null;
			}
		}, new RemoteClientConnector() {
			
			@Override
			public void disconnect() {
				
				
			}
			
			@Override
			public void connect(ConnectionCallback arg0) {
				
				
			}
		}

*/