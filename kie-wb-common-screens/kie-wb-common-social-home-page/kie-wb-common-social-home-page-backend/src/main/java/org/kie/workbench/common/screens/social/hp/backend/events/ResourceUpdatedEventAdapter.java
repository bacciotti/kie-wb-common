package org.kie.workbench.common.screens.social.hp.backend.events;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.kie.uberfire.social.activities.model.SocialActivitiesEvent;
import org.kie.uberfire.social.activities.model.SocialEventType;
import org.kie.uberfire.social.activities.model.SocialUser;
import org.kie.uberfire.social.activities.service.SocialAdapter;
import org.kie.uberfire.social.activities.service.SocialCommandTypeFilter;
import org.kie.uberfire.social.activities.service.SocialUserRepositoryAPI;
import org.kie.workbench.common.screens.social.hp.model.HomePageTypes;
import org.uberfire.security.Identity;
import org.uberfire.workbench.events.ResourceUpdatedEvent;

@ApplicationScoped
public class ResourceUpdatedEventAdapter implements SocialAdapter<ResourceUpdatedEvent> {

    @Inject
    private Identity loggedUser;

    @Inject
    private SocialUserRepositoryAPI socialUserRepositoryAPI;

    @Override
    public Class<ResourceUpdatedEvent> eventToIntercept() {
        return ResourceUpdatedEvent.class;
    }

    @Override
    public SocialEventType socialEventType() {
        return HomePageTypes.RESOURCE_UPDATE_EVENT;
    }

    @Override
    public boolean shouldInterceptThisEvent( Object event ) {
        if ( event.getClass().getSimpleName().equals( eventToIntercept().getSimpleName() ) ) {
            return true;
        }
        return false;
    }

    @Override
    public SocialActivitiesEvent toSocial( Object object ) {
        ResourceUpdatedEvent event = (ResourceUpdatedEvent) object;
        SocialUser socialUser = socialUserRepositoryAPI.findSocialUser( event.getSessionInfo().getIdentity().getName() );
        String aditionalInfo = "edited ";
        return new SocialActivitiesEvent( socialUser, HomePageTypes.RESOURCE_UPDATE_EVENT.name(), new Date() ).withLink( event.getPath().getFileName(), event.getPath().toURI() ).withAdicionalInfo( aditionalInfo );
    }

    @Override
    public List<SocialCommandTypeFilter> getTimelineFilters() {
        ArrayList<SocialCommandTypeFilter> socialCommandTypeFilters = new ArrayList<SocialCommandTypeFilter>();
        return socialCommandTypeFilters;
    }

    @Override
    public List<String> getTimelineFiltersNames() {
        List<String> names = new ArrayList<String>();
        return names;
    }
}
