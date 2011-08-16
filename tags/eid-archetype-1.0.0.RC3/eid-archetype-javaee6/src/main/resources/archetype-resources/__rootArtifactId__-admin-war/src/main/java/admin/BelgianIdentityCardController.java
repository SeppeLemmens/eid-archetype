package ${package}.admin;

import java.io.IOException;
import java.io.OutputStream;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import be.fedict.eid.applet.service.Identity;

@Named(Constants.CDI_PREFIX + "eIDController")
public class BelgianIdentityCardController {

	@Inject
	private HttpSession httpSession;

	public void createPhoto(OutputStream outputStream, Object data)
			throws IOException {
		byte[] photo = (byte[]) this.httpSession.getAttribute("eid.photo");
		outputStream.write(photo);
	}
}
