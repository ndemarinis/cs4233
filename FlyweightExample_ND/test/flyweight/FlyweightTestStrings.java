/**
 * This file was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package flyweight;

/**
 * This class provides test strings for
 * my Flyweight tests.  
 * @author ndemarinis
 * @version Feb 20 2013
 */
public class FlyweightTestStrings {

	public static final String loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam tempor, " +
			"ipsum quis vestibulum blandit, nulla neque eleifend nulla, vitae tristique risus turpis non " +
			"ante. Aliquam erat volutpat. Pellentesque habitant morbi tristique senectus et netus et malesuada " +
			"fames ac turpis egestas. Proin metus nibh, pellentesque in tincidunt in, pulvinar euismod augue. " +
			"Sed at lorem eu lacus molestie molestie. Vivamus molestie euismod condimentum. Curabitur dui mi, " +
			"aliquet egestas facilisis ac, consequat nec diam. Nulla facilisi. Suspendisse pellentesque purus et " +
			"lorem semper sed ultricies dolor lacinia. Sed feugiat sagittis velit, ac volutpat metus ornare pulvinar. " +
			"Curabitur dolor neque, tincidunt vel ultricies eu, faucibus eget nunc. Mauris ultricies turpis ut ipsum " +
			"tempor consectetur. Praesent sapien ligula, vulputate et fermentum at, auctor placerat ante. Nullam vehicula " +
			"gravida metus, id iaculis magna dictum eu. Cras sollicitudin massa quis magna tempus molestie. Aliquam commodo " +
			"facilisis feugiat. Vivamus magna tortor, ullamcorper vel tempus quis, aliquam ut lorem. Maecenas ac lacus nisi." +
			" Donec quis ante turpis. Vestibulum id tempus nibh. Vivamus non sapien diam, a pretium magna. Nullam nulla " +
			"lorem, cursus bibendum pellentesque vitae, ultrices ut lorem. Aenean rutrum purus in elit bibendum malesuada. " +
			"Sed dictum eros non sapien imperdiet eget convallis augue ultrices. Sed nec justo dolor, ac vulputate tortor. " +
			"Ut dolor felis, venenatis non aliquet eu, aliquam at leo.Pellentesque tempor imperdiet nisi ac dignissim. Phasellus " +
			"ut libero nec quam auctor suscipit. Cras congue nulla quam. Aliquam porttitor augue ac lacus hendrerit ultrices ac " +
			"a dui. Aenean imperdiet justo vel enim venenatis a pellentesque diam suscipit. Praesent eleifend, nibh quis suscipit " +
			"suscipit, lorem arcu interdum diam, ac semper tellus nisl vel quam. Sed eleifend fermentum lectus in gravida. Integer " +
			"suscipit porta lorem sed commodo. Nam in ipsum ac elit imperdiet vehicula eget eget risus.Nulla felis metus, " +
			"imperdiet semper hendrerit et, fringilla at dolor. Sed cursus porta elit, ut fermentum nunc placerat ac. Sed bibendum " +
			"sollicitudin mollis. Donec faucibus fermentum pretium. Etiam quis neque nulla. Suspendisse rutrum felis nec ligula " +
			"scelerisque vitae sollicitudin urna sodales. Aliquam posuere fermentum velit, sit amet sodales nisl cursus et. " +
			"Quisque cursus ullamcorper urna, ut laoreet augue imperdiet et. Sed vehicula scelerisque lectus, id gravida urna " +
			"ullamcorper ut. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Class aptent " +
			"taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec ut ultrices nibh.Duis vitae " +
			"est sit amet nunc consectetur commodo. Morbi iaculis mattis odio vitae dapibus. Nam eu massa in risus venenatis cursus " +
			"vel id turpis. Cras ullamcorper varius eros. Maecenas eget scelerisque tortor. Vestibulum mollis nunc euismod nibh " +
			"pellentesque ac consectetur mi ultricies. Suspendisse quis arcu felis. Ut ut eros sapien, in dictum est. Mauris eget " +
			"dapibus arcu.";	
	
	public static final String loremIpsumModified = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam tempor, " +
			"ipsum quis vestibulum blandit, nulla neque eleifend nulla, vitae tristique risus turpis non " +
			"ante. Aliquam erat volutpat. Pellentesque habitant morbi tristique senectus et netus et malesuada " +
			"fames ac turpis egestas. Proin metus nibh, pellentesque in tincidunt in, pulvinar euismod augue. " +
			"Sed at lorem eu lacus molestie molestie. Vivamus molestie euismod condimentum. Curabitur dui mi, " +
			"aliquet egestas facilisis ac, consequat nec diam. Nulla facilisi. Suspendisse pellentesque purus et " +
			"lorem semper sed ultricies dolor lacinia. Sed feugiat sagittis velit, ac volutpat metus ornare pulvinar. " +
			"Curabitur dolor neque, tincidunt vel ultricies eu, faucibus eget nunc. Mauris ultricies turpis ut ipsum " +
			"tempor consectetur. Praesent sapien ligula, vulputate et fermentum at, auctor placerat ante. Nullam vehicula " +
			"gravida metus, id iaculis magna dictum eu. Cras sollicitudin massa quis magna tempus molestie. Aliquam commodo " +
			"facilisis feugiat. Vivamus magna tortor, ullamcorper vel tempus quis, aliquam ut lorem. Maecenas ac lacus nisi." +
			" Donec quis ante turpis. Vestibulum id tempus nibh. Vivamus non sapien diam, a pretium magna. Nullam nulla " +
			"lorem, cursus bibendum pellentesque vitae, ultrices ut lorem. Aenean rutrum purus in elit bibendum malesuada. " +
			"Sed dictum eros non sapien imperdiet eget convallis augue ultrices. Sed nec justo dolor, ac vulputate tortor. " +
			"Ut dolor felis, venenatis non aliquet eu, aliquam at leo.Pellentesque tempor imperdiet nisi ac dignissim. Phasellus " +
			"ut libero nec quam auctor suscipit. Cras congue nulla quam. Aliquam porttitor augue ac lacus hendrerit ultrices ac " +
			"a dui. Aenean imperdiet justo vel enim venenatis a pellentesque diam suscipit. Praesent eleifend, nibh quis suscipit " +
			"suscipit, lorem arcu interdum diam, ac semper tellus nisl vel quam. Sed eleifend fermentum lectus in gravida. Integer " +
			"suscipit porta lorem sed commodo. Nam in ipsum ac elit imperdiet vehicula eget eget risus.Nulla felis metus, " +
			"imperdiet semper hendrerit et, fringilla at dolor. Sed cursus porta elit, ut fermentum nunc placerat ac. Sed bibendum " +
			"sollicitudin mollis. Donec faucibus fermentum pretium. Etiam quis neque nulla. Suspendisse rutrum felis nec ligula " +
			"scelerisque vitae sollicitudin urna sodales. Aliquam posuere fermentum velit, sit amet sodales nisl cursus et. " +
			"Quisque cursus ullamcorper urna, ut laoreet augue imperdiet et. Sed vehicula scelerisque lectus, id gravida urna " +
			"ullamcorper ut. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Class aptent " +
			"taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec ut ultrices nibh.Duis vitae " +
			"est sit amet nunc consectetur commodo. Morbi iaculis mattis odio vitae dapibus. Nam eu massa in risus venenatis cursus " +
			"vel id turpis. Cras ullamcorper varius eros. Maecenas eget scelerisque tortor. Vestibulum mollis nunc euismod nibh " +
			"pellentesque ac consectetur mi ultricies. Suspendisse quis arcu felis. Ut ut eros sapien, in dictum est. Mauris eget " +
			"dapibus arcu!";	

}
