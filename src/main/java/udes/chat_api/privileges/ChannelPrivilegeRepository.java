package udes.chat_api.privileges;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelPrivilegeRepository extends CrudRepository<ChannelPrivilege, Long>
{

    @Override
    List<ChannelPrivilege> findAll();

    List<ChannelPrivilege> findByUserCip(String cip);

    List<ChannelPrivilege> findByChannelChannelId(int channelId);

    List<ChannelPrivilege> findByChannelChannelIdAndType(int roomId, int type);

    ChannelPrivilege findByUserCipAndChannelChannelId(String cip, int roomId);
}
