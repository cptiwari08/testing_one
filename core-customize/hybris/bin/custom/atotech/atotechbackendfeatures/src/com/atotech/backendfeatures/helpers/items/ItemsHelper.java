package com.atotech.backendfeatures.helpers.items;

import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.servicelayer.session.SessionExecutionBody;
import de.hybris.platform.servicelayer.session.SessionService;
import de.hybris.platform.servicelayer.user.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

import static com.atotech.backendfeatures.matchers.ItemMatcher.hasExpectedValues;
import static org.hamcrest.MatcherAssert.assertThat;

@Component
public class ItemsHelper {

    @Resource
    private SessionService sessionService;
    @Resource
    private UserService userService;

    public void assertThatItemHasExpectedValues(ItemModel item, Map<String, String> expectedValues) {
        sessionService.executeInLocalView(new SessionExecutionBody() {
            @Override
            public void executeWithoutResult() {
                userService.setCurrentUser(userService.getAdminUser());
                assertThat(item, hasExpectedValues(expectedValues));
            }
        });
    }
}
