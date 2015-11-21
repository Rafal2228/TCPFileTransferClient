package skj.raf.client.model;

import java.util.Date;

import skj.raf.client.controller.RenderCtrl;

public class SpeedCounter {

	private static final String PRE_CONSOLE = "SpeedCounter: ";
	
	private enum Multiply {
		KILO (1024, "KB/s"),
		MEGA (1048576, "MB/s"),
		GIGA (1073741824, "GB/s");
		
		private int _size;
		private String _postfix;
		
		Multiply(int size, String postfix) {
			_size = size;
			_postfix = postfix;
		}
		
		public int getSize() {
			return _size;
		}
		
		public String toString(int current) {
			return "," + current/_size + _postfix;
		}
	}
	
	private int _transfered;
	private int _prefix;
	private Multiply _postfix;
	
	private boolean _uploading;
	private Date _startDate;
	private Date _updateDate;
	
	public SpeedCounter() {
		_transfered = 0;
		_prefix = 0;
		_postfix = Multiply.KILO;
		_uploading = false;
	}
	
	public void start() {
		if(!_uploading) {
			_uploading = true;
			_startDate = new Date();
			_updateDate = _startDate;
		}
	}
	
	public void end() {
		_uploading = false;
		_transfered = 0;
		_prefix = 0;
		_postfix = Multiply.KILO;
	}
	
	public void updateSpeed(int block) {
		if(_uploading) {
			_transfered += block;
			
			if(_transfered > _postfix.getSize()) {
				_prefix += _transfered / _postfix.getSize();
				_transfered %= _postfix.getSize();
				
				if(_prefix >= 1024) {
					switch(_postfix){
						case KILO: {
							_postfix = Multiply.MEGA;
							_prefix %= 1024;
						} break;
						case MEGA: {
							_postfix = Multiply.GIGA;
							_prefix %= 1024;
						} break;
						default: break;
					}
				}
			}
			
			Date now = new Date();
			
			if(now.getTime() - _updateDate.getTime() > 1000) {
				updateView();
			}
		} else {
			System.out.println(PRE_CONSOLE + "Error, there is no upload going on");
		}
	}
	
	private void updateView() {
		RenderCtrl.updateSpeed(_prefix + _postfix.toString(_transfered));
		_updateDate = new Date();
		_prefix = 0;
		_postfix = Multiply.KILO;
	}
}
