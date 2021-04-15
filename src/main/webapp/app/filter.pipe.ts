import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'filtercs',
})
export class FilterPipe implements PipeTransform {
  transform(items: any[], filter: any): any {
    if (items.length === 0) {
      return items;
    }

    if (filter) {
      const filterKeys = Object.keys(filter);

      return items.filter(item => {
        return filterKeys.some(keyName => {
          if (filter[keyName] === '') {
            return true;
          }
          return new RegExp(filter[keyName], 'gi').test(item[keyName]);
        });
      });
    } else {
      return items;
    }
  }
}
