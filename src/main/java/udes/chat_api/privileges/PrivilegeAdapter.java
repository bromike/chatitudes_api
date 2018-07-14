package udes.chat_api.privileges;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import udes.chat_api.channels.ChannelRepository;
import udes.chat_api.rooms.RoomRepository;
import udes.chat_api.users.UserRepository;

@Service
public class PrivilegeAdapter
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomPrivilegeRepository roomPrivilegeRepository;
    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private ChannelPrivilegeRepository channelPrivilegeRepository;

    public RoomPrivilegeDto toDto(RoomPrivilege roomPrivilege)
    {
        RoomPrivilegeDto roomPrivilegeDto = new RoomPrivilegeDto();

        roomPrivilegeDto.setUserCip(roomPrivilege.getUser().getCip());

        roomPrivilegeDto.setRoomId(roomPrivilege.getRoom().getRoomId());

        roomPrivilegeDto.setType(roomPrivilege.getType());

        return roomPrivilegeDto;
    }

    public RoomPrivilege toEntity(RoomPrivilegeDto roomPrivilegeDto)
    {
        RoomPrivilege existingRoomPrivilege = roomPrivilegeRepository.findByUserCipAndRoomRoomId(roomPrivilegeDto.getUserCip(), roomPrivilegeDto.getRoomId());

        if(existingRoomPrivilege != null)
        {
            existingRoomPrivilege.setType(roomPrivilegeDto.getType());
            return existingRoomPrivilege;
        }

        RoomPrivilege roomPrivilege = new RoomPrivilege();

        roomPrivilege.setUser(userRepository.findByCip(roomPrivilegeDto.getUserCip()));
        roomPrivilege.setRoom(roomRepository.findByRoomIdAndIsDeletedFalse(roomPrivilegeDto.getRoomId()));
        roomPrivilege.setType(roomPrivilegeDto.getType());

        return roomPrivilege;
    }

    public ChannelPrivilegeDto toDto(ChannelPrivilege channelPrivilege)
    {
        ChannelPrivilegeDto channelPrivilegeDto = new ChannelPrivilegeDto();

        channelPrivilegeDto.setUserCip(channelPrivilege.getUser().getCip());

        channelPrivilegeDto.setChannelId(channelPrivilege.getChannel().getChannelId());

        channelPrivilegeDto.setType(channelPrivilege.getType());

        return channelPrivilegeDto;
    }

    public ChannelPrivilege toEntity(ChannelPrivilegeDto channelPrivilegeDto)
    {
        ChannelPrivilege existingChannelPrivilege = channelPrivilegeRepository.findByUserCipAndChannelChannelId(channelPrivilegeDto.getUserCip(), channelPrivilegeDto.getChannelId());

        if(existingChannelPrivilege != null)
        {
            existingChannelPrivilege.setType(channelPrivilegeDto.getType());
            return existingChannelPrivilege;
        }

        ChannelPrivilege channelPrivilege = new ChannelPrivilege();

        channelPrivilege.setUser(userRepository.findByCip(channelPrivilegeDto.getUserCip()));
        channelPrivilege.setChannel(channelRepository.findByChannelIdAndIsDeletedFalse(channelPrivilegeDto.getChannelId()));
        channelPrivilege.setType(channelPrivilegeDto.getType());

        return channelPrivilege;
    }
}
