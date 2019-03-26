package ci.weget.web.modeles.firebase;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.UserRecord.UpdateRequest;

@Service
public class AuthSnippets {
	public static void getUserByEmail(String email) throws InterruptedException, ExecutionException {
		// [START get_user_by_email]
		UserRecord userRecord = FirebaseAuth.getInstance().getUserByEmailAsync(email).get();
		// See the UserRecord reference doc for the contents of userRecord.
		System.out.println("Successfully fetched user data: " + userRecord.getEmail());
		// [END get_user_by_email]
	}

	public static void getUserByPhoneNumber(String phoneNumber) throws InterruptedException, ExecutionException {
		// [START get_user_by_phone]
		UserRecord userRecord = FirebaseAuth.getInstance().getUserByPhoneNumberAsync(phoneNumber).get();
		// See the UserRecord reference doc for the contents of userRecord.
		System.out.println("Successfully fetched user data: " + userRecord.getPhoneNumber());
		// [END get_user_by_phone]
	}

	public static void createUser() throws InterruptedException, ExecutionException {
		// [START create_user]
		CreateRequest request = new CreateRequest().setEmail("user@example.com").setEmailVerified(false)
				.setPassword("secretPassword").setPhoneNumber("+11234567890").setDisplayName("John Doe")
				.setPhotoUrl("http://www.example.com/12345678/photo.png").setDisabled(false);

		UserRecord userRecord = FirebaseAuth.getInstance().createUserAsync(request).get();
		System.out.println("Successfully created new user: " + userRecord.getUid());
		// [END create_user]
	}

	public static void updateUser(String uid) throws InterruptedException, ExecutionException {
		// [START update_user]
		UpdateRequest request = new UpdateRequest(uid).setEmail("user@example.com").setPhoneNumber("+11234567890")
				.setEmailVerified(true).setPassword("newPassword").setDisplayName("Jane Doe")
				.setPhotoUrl("http://www.example.com/12345678/photo.png").setDisabled(true);

		UserRecord userRecord = FirebaseAuth.getInstance().updateUserAsync(request).get();
		System.out.println("Successfully updated user: " + userRecord.getUid());
		// [END update_user]
	}

}
