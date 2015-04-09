package net.dorokhov.pony.web.client.mvp.library;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.web.bindery.event.shared.EventBus;
import net.dorokhov.pony.web.client.control.AnchorCell;
import net.dorokhov.pony.web.client.control.PagedListView;
import net.dorokhov.pony.web.client.mvp.common.ModalViewWithUiHandlers;
import net.dorokhov.pony.web.client.resource.Messages;
import net.dorokhov.pony.web.client.service.common.OperationCallback;
import net.dorokhov.pony.web.client.service.common.OperationRequest;
import net.dorokhov.pony.web.shared.PagedListDto;
import net.dorokhov.pony.web.shared.UserDto;
import org.gwtbootstrap3.client.ui.Anchor;
import org.gwtbootstrap3.client.ui.Modal;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;

public class UserListView extends ModalViewWithUiHandlers<UserListUiHandlers> implements UserListPresenter.MyView {

	interface MyUiBinder extends UiBinder<Modal, UserListView> {}

	@SuppressWarnings("GwtCssResourceErrors")
	interface MyStyle extends CssResource {

		String userRole();

		String userRoleUser();
		String userRoleAdmin();

	}

	private static final MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	private static final DateTimeFormat DATE_FORMAT = DateTimeFormat.getFormat(Messages.INSTANCE.dateFormatTechnical());

	@UiField
	MyStyle style;

	@UiField(provided = true)
	PagedListView<UserDto> userView;

	@Inject
	public UserListView(EventBus aEventBus) {

		super(aEventBus);

		final List<String> headers = Arrays.asList(
				Messages.INSTANCE.userListColumnCreationDate(),
				Messages.INSTANCE.userListColumnUpdateDate(),
				Messages.INSTANCE.userListColumnName(),
				Messages.INSTANCE.userListColumnEmail(),
				Messages.INSTANCE.userListColumnRole()
		);
		final List<String> widths = Arrays.asList(
				"150px", "150px", null, "250px", "80px"
		);
		final List<Column<UserDto, ?>> columns = Arrays.asList(
				new TextColumn<UserDto>() {
					@Override
					public String getValue(UserDto aUser) {
						return DATE_FORMAT.format(aUser.getCreationDate());
					}
				},
				new TextColumn<UserDto>() {
					@Override
					public String getValue(UserDto aUser) {
						return aUser.getUpdateDate() != null ? DATE_FORMAT.format(aUser.getUpdateDate()) : "";
					}
				},
				new TextColumn<UserDto>() {
					@Override
					public String getValue(UserDto aUser) {
						return aUser.getName();
					}
				},
				new Column<UserDto, Anchor>(new AnchorCell()) {
					@Override
					public Anchor getValue(UserDto aUser) {
						return new Anchor(aUser.getEmail(), "mailto:" + aUser.getEmail());
					}
				},
				new TextColumn<UserDto>() {

					@Override
					public String getValue(UserDto aUser) {

						switch (aUser.getRole()) {
							case USER:
								return Messages.INSTANCE.userListRoleUser();
							case ADMIN:
								return Messages.INSTANCE.userListRoleAdmin();
						}

						return String.valueOf(aUser.getRole());
					}

					@Override
					public String getCellStyleNames(Cell.Context aContext, UserDto aUser) {

						String result = style.userRole() + " ";

						switch (aUser.getRole()) {
							case USER:
								result += style.userRoleUser();
								break;
							case ADMIN:
								result += style.userRoleAdmin();
								break;
						}

						return result;
					}
				}
		);

		userView = new PagedListView<>(new PagedListView.DataSource<UserDto>() {

			@Override
			public int getColumnCount() {
				return columns.size();
			}

			@Override
			public Column<UserDto, ?> getColumn(int aIndex) {
				return columns.get(aIndex);
			}

			@Override
			public String getColumnWidth(int aIndex) {
				return widths.get(aIndex);
			}

			@Override
			public String getHeader(int aIndex) {
				return headers.get(aIndex);
			}

			@Override
			public String getPagerLabel(PagedListDto<UserDto> aPagedList) {
				return Messages.INSTANCE.userListPager(aPagedList.getPageNumber() + 1, aPagedList.getTotalPages(), aPagedList.getContent().size(), aPagedList.getTotalElements());
			}

			@Override
			public OperationRequest requestPagedList(int aPageNumber, OperationCallback<PagedListDto<UserDto>> aCallback) {
				return getUiHandlers().onUsersRequested(aPageNumber, aCallback);
			}
		});

		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void reloadUsers(boolean aClearData) {
		userView.reload(aClearData);
	}

}
