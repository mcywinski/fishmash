module ApplicationHelper
  # CONSTANTS

  DEFAULT_OPTION_ID = -1

  # METHODS

  # Transforms a collection of items into select options.
  # Collection members must contain id and name fields.
  def create_select_options(collection, default_item_name)
    default_item_name = 'value' if default_item_name.blank?

    prepared_items = Array.new
    prepared_items.push ['Select a ' + default_item_name.downcase, DEFAULT_OPTION_ID]

    collection.each do |item|
      prepared_items.push [item.name, item.id]
    end
    options_for_select(prepared_items)
  end
end
