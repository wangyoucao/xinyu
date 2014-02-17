/**
 * monitor the update of the download
 * @author zhangjie
 * @version 1.0
 * @since 2012-5-13
 * */

package together.connectivity;

public interface UpdateListener {

	/**
	 * called when process is updated
	 * 
	 * @param process
	 *            the process of download
	 * */
	public void updateProcess(int process);

}
