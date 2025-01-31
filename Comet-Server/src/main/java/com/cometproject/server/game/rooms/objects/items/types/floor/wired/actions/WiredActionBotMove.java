package com.cometproject.server.game.rooms.objects.items.types.floor.wired.actions;

import com.cometproject.api.game.rooms.objects.data.RoomItemData;
import com.cometproject.api.game.utilities.Position;
import com.cometproject.server.game.rooms.objects.entities.types.BotEntity;
import com.cometproject.server.game.rooms.objects.items.RoomItemFloor;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.WiredUtil;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.base.WiredActionItem;
import com.cometproject.server.game.rooms.objects.items.types.floor.wired.events.WiredItemEvent;
import com.cometproject.server.game.rooms.types.Room;


public class WiredActionBotMove extends WiredActionItem {

    public WiredActionBotMove(RoomItemData itemData, Room room) {
        super(itemData, room);
    }

    @Override
    public void onEventComplete(WiredItemEvent event) {
        if (this.getWiredData() == null || this.getWiredData().getSelectedIds() == null || this.getWiredData().getSelectedIds().isEmpty()) {
            return;
        }

        final Long itemId = WiredUtil.getRandomElement(this.getWiredData().getSelectedIds());

        if (itemId == null) {
            return;
        }

        final RoomItemFloor item = this.getRoom().getItems().getFloorItem(itemId);

        if (item == null || item.isAtDoor() || item.getPosition() == null || item.getTile() == null) {
            return;
        }

        final Position position = new Position(item.getPosition().getX(), item.getPosition().getY());
        final String entityName = this.getWiredData().getText();
        final BotEntity botEntity = this.getRoom().getBots().getBotByName(entityName);

        if (botEntity == null) {
            return;
        }

        botEntity.moveTo(position);
    }


    @Override
    public int getInterface() {
        return 22;
    }

    @Override
    public boolean requiresPlayer() {
        return false;
    }
}
